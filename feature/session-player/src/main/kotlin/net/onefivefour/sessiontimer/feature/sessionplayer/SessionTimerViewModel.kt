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
import net.onefivefour.sessiontimer.core.usecases.api.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.domain.SessionCompiler
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiSession
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTask
import net.onefivefour.sessiontimer.feature.sessionplayer.model.TimerState

@HiltViewModel
internal class SessionTimerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSessionUseCase: GetSessionUseCase,
    private val getTimerStatusUseCase: GetTimerStatusUseCase,
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val resetTimerUseCase: ResetTimerUseCase
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
                _uiSessionFlow.value = SessionCompiler.compile(session)
            }
        }
    }

    private suspend fun updateTimerState() {
        combine(
            uiSessionFlow.filterNotNull(),
            getTimerStatusUseCase.execute()
        ) { uiSession, timerStatus ->
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
            elapsedDuration >= sumOfPastTaskDurations -> null
            else -> taskList.last()
        }
    }

    fun onStartSession() {
        val timerState = _timerState.value
        if (timerState is TimerState.Active && timerState.elapsedTotalDuration == Duration.ZERO) {
            startTimerUseCase.execute(timerState.totalDuration)
        }
    }

    fun onPauseSession() {
        pauseTimerUseCase.execute()
    }

    fun onResetSession() {
        resetTimerUseCase.execute()
    }

    fun onNextTask() {
//        seekTimerUseCase.execute()
    }

    fun onPreviousTask() {
//        resetTimerUseCase.execute()
    }
}
