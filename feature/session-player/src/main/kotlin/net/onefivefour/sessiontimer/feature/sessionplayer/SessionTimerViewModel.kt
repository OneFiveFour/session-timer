package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.Duration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.GetTimerStatusUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.InitSessionTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.SeekTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.domain.SessionCompiler
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiSession
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTask
import net.onefivefour.sessiontimer.feature.sessionplayer.model.TimerState
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
internal class SessionTimerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionCompiler: SessionCompiler,
    private val initSessionTimerUseCase: InitSessionTimerUseCase,
    private val getSessionUseCase: GetSessionUseCase,
    private val getTimerStatusUseCase: GetTimerStatusUseCase,
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val resetTimerUseCase: ResetTimerUseCase,
    private val seekTimerUseCase: SeekTimerUseCase
) : ViewModel() {

    private val sessionId = savedStateHandle.toRoute<SessionPlayerRoute>().sessionId

    private val _uiSessionFlow = MutableStateFlow<UiSession?>(null)
    private val uiSessionFlow = _uiSessionFlow.asStateFlow()

    private var _timerState = MutableStateFlow<TimerState>(TimerState.Initial())
    val timerState = _timerState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                compileSession()
            }
            launch {
                updateTimerState()
            }
        }
    }

    private suspend fun compileSession() {
        getSessionUseCase.execute(sessionId).collect { session ->
            if (session != null && _uiSessionFlow.value == null) {
                _uiSessionFlow.value = sessionCompiler.compile(session)
            }
        }
    }

    private suspend fun updateTimerState() {
        combine(
            uiSessionFlow.filterNotNull(),
            getTimerStatusUseCase.execute()
        ) { uiSession, timerStatus ->
            initSessionTimerUseCase.execute(uiSession.totalDuration)
            computeTimerState(uiSession, timerStatus)
        }.collect { newTimerState ->
            _timerState.update { newTimerState }
        }
    }

    private fun computeTimerState(
        uiSession: UiSession,
        timerStatus: TimerStatus
    ): TimerState {

        if (uiSession.taskList.isEmpty()) {
            return TimerState.Initial()
        }

        if (timerStatus.mode == TimerMode.FINISHED) {
            return TimerState.Finished
        }

        val tasks = uiSession.taskList

        val currentTask = determineCurrentTask(
            timerStatus.elapsedDuration,
            tasks
        )

        var elapsedTasksDuration = Duration.ZERO
        for (task in tasks) {
            if (task == currentTask) break
            elapsedTasksDuration += task.taskDuration
        }

        val elapsedTaskDuration = timerStatus.elapsedDuration - elapsedTasksDuration

        val isRunning = timerStatus.mode == TimerMode.RUNNING

        return TimerState.Active(
            isRunning = isRunning,
            totalDuration = uiSession.totalDuration,
            elapsedTaskDuration = elapsedTaskDuration,
            elapsedTotalDuration = timerStatus.elapsedDuration,
            currentTask = currentTask,
            tasks = tasks
        )
    }

    private fun determineCurrentTask(
        elapsedDuration: Duration,
        taskList: List<UiTask>
    ): UiTask? {
        var sumOfPastTaskDurations = Duration.ZERO

        for (task in taskList) {
            sumOfPastTaskDurations += task.taskDuration
            if (elapsedDuration < sumOfPastTaskDurations) {
                return task
            }
        }

        return when {
            elapsedDuration > sumOfPastTaskDurations -> null
            else -> taskList.last()
        }
    }

    fun onStartSession() {
        val timerState = _timerState.value
        if (timerState is TimerState.Active && !timerState.isRunning) {
            startTimerUseCase.execute()
        }
    }

    fun onPauseSession() {
        pauseTimerUseCase.execute()
    }

    fun onResetSession() {
        resetTimerUseCase.execute()
    }

    fun onNextTask() {
        val state = _timerState.value as? TimerState.Active ?: return

        val seekTo = when (state.currentTask) {
            null -> state.tasks.firstOrNull()?.taskDuration ?: return
            else -> state.tasks
                .takeWhile { it != state.currentTask }
                .fold(Duration.ZERO) { acc, task -> acc + task.taskDuration }
                .plus(state.currentTask.taskDuration)
        }

        seekTimerUseCase.execute(seekTo)
    }

    fun onPreviousTask() {

        val state = _timerState.value
        if (state is TimerState.Finished) {
            val session = _uiSessionFlow.value ?: return
            val lastTaskDuration = session.taskList.last().taskDuration
            seekTimerUseCase.execute(session.totalDuration - lastTaskDuration)
        }


        if (state !is TimerState.Active) return

        val seekTo = when (state.currentTask) {
            null -> Duration.ZERO
            else -> {

                val previousTasks = state.tasks
                    .takeWhile { it.id != state.currentTask.id }

                val previousTasksDuration = previousTasks
                    .fold(Duration.ZERO) { acc, uiTask -> acc + uiTask.taskDuration }

                // check if we go to the previous task or restart the current task
                val shouldStartPreviousTask =
                    state.elapsedTotalDuration - previousTasksDuration < 500.milliseconds

                when {
                    shouldStartPreviousTask -> {
                        previousTasks
                            .dropLast(1)
                            .fold(Duration.ZERO) { acc, uiTask -> acc + uiTask.taskDuration }
                    }

                    else -> previousTasksDuration
                }
            }
        }

        seekTimerUseCase.execute(seekTo)
    }

    fun onDispose() {
        onResetSession()
        sessionCompiler.reset()
    }
}
