package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.api.model.TimerState
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
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTimerState

@HiltViewModel
internal class SessionTimerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionCompiler: SessionCompiler,
    private val initSessionTimer: InitSessionTimerUseCase,
    private val getSession: GetSessionUseCase,
    private val getTimerStatus: GetTimerStatusUseCase,
    private val startTimer: StartTimerUseCase,
    private val pauseTimer: PauseTimerUseCase,
    private val resetTimer: ResetTimerUseCase,
    private val seekTimer: SeekTimerUseCase
) : ViewModel() {

    private val sessionId = savedStateHandle.toRoute<SessionPlayerRoute>().sessionId

    private val _uiSessionFlow = MutableStateFlow<UiSession?>(null)
    private val uiSessionFlow = _uiSessionFlow.asStateFlow()

    private val _uiTimerState = MutableStateFlow<UiTimerState>(UiTimerState.Initial())
    val uiTimerState = _uiTimerState.asStateFlow()

    init {
        viewModelScope.launch {
            compileSession(this)
            updateTimerState(this)
        }
    }

    private suspend fun compileSession(scope: CoroutineScope) {
        getSession.execute(sessionId)
            .filterNotNull()
            .filter { _uiSessionFlow.value == null }
            .map { session -> sessionCompiler.compile(session) }
            .onEach { session ->
                initSessionTimer.execute(session.totalDuration)
                _uiSessionFlow.value = session
            }
            .launchIn(scope)
    }

    private fun updateTimerState(scope: CoroutineScope) {
        combine(
            uiSessionFlow.filterNotNull(),
            getTimerStatus.execute()
        ) { uiSession, timerStatus ->
            computeTimerState(uiSession, timerStatus)
        }.onEach { newTimerState ->
            _uiTimerState.update { newTimerState }
        }.launchIn(scope)
    }

    private fun computeTimerState(uiSession: UiSession, timerState: TimerState): UiTimerState {
        if (uiSession.taskList.isEmpty()) {
            return UiTimerState.Initial()
        }

        if (timerState.mode == TimerMode.FINISHED) {
            return UiTimerState.Finished
        }

        val tasks = uiSession.taskList

        val currentTask = findCurrentTask(
            timerState.elapsedDuration,
            tasks
        )

        val elapsedTasksDuration = tasks
            .takeWhile { it != currentTask }
            .fold(Duration.ZERO) { acc, task -> acc + task.taskDuration }

        val elapsedTaskDuration = timerState.elapsedDuration - elapsedTasksDuration

        val isRunning = timerState.mode == TimerMode.RUNNING

        return UiTimerState.Active(
            isRunning = isRunning,
            totalDuration = uiSession.totalDuration,
            elapsedTaskDuration = elapsedTaskDuration,
            elapsedTotalDuration = timerState.elapsedDuration,
            currentTask = currentTask,
            tasks = tasks
        )
    }

    private fun findCurrentTask(elapsedDuration: Duration, tasks: List<UiTask>): UiTask? {
        var accumulatedDuration = Duration.ZERO
        return tasks.firstOrNull { task ->
            accumulatedDuration += task.taskDuration
            elapsedDuration < accumulatedDuration
        }
    }

    fun onStartSession() {
        val timerState = _uiTimerState.value
        if (timerState is UiTimerState.Active && !timerState.isRunning) {
            startTimer.execute()
        }
    }

    fun onPauseSession() {
        pauseTimer.execute()
    }

    fun onResetSession() {
        resetTimer.execute()
    }

    fun onNextTask() {
        val activeState = _uiTimerState.value as? UiTimerState.Active ?: return

        val seekTo = when (activeState.currentTask) {
            null -> activeState.tasks.firstOrNull()?.taskDuration ?: return
            else ->
                activeState.tasks
                    .takeWhile { it != activeState.currentTask }
                    .fold(Duration.ZERO) { acc, task -> acc + task.taskDuration }
                    .plus(activeState.currentTask.taskDuration)
        }

        seekTimer.execute(seekTo)
    }

    fun onPreviousTask() {
        val state = _uiTimerState.value
        if (state is UiTimerState.Finished) {
            val session = _uiSessionFlow.value ?: return
            val lastTaskDuration = session.taskList.last().taskDuration
            seekTimer.execute(session.totalDuration - lastTaskDuration)
        }

        if (state !is UiTimerState.Active) return

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

        seekTimer.execute(seekTo)
    }

    fun onDispose() {
        onResetSession()
        sessionCompiler.reset()
    }
}
