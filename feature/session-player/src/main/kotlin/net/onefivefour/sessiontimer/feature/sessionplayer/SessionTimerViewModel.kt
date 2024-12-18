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
    getSessionUseCase: GetSessionUseCase,
    getTimerStatusUseCase: GetTimerStatusUseCase,
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val resetTimerUseCase: ResetTimerUseCase
) : ViewModel() {

    private val sessionId = savedStateHandle.toRoute<SessionPlayerRoute>().sessionId

    private lateinit var compiledSession: UiSession

    private var _timerState = MutableStateFlow<TimerState>(TimerState.Initial())
    val timerState = _timerState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getSessionUseCase.execute(sessionId),
                getTimerStatusUseCase.execute()
            ) { session, timerStatus ->

                if (session == null) {
                    return@combine TimerState.Initial()
                }

                if (!::compiledSession.isInitialized) {
                    compiledSession = SessionCompiler.compile(session)
                }

                when {
                    compiledSession.taskList.isEmpty() -> TimerState.Initial()
                    else -> computeTimerState(compiledSession, timerStatus)
                }
            }.collect { newTimerState ->
                _timerState.update { newTimerState }
            }
        }
    }

    private fun computeTimerState(
        compiledSession: UiSession,
        timerStatus: TimerStatus
    ): TimerState {

        if (timerStatus.mode == TimerMode.FINISHED) {
            return TimerState.Finished
        }

        val tasks = compiledSession.taskList

        val currentTask = determineCurrentTask(
            timerStatus.elapsedDuration,
            tasks
        )

        val elapsedTasksDuration = tasks
            .takeWhile { it != currentTask }
            .fold(Duration.ZERO) { acc, task -> acc + task.taskDuration }


        val elapsedTaskDuration = timerStatus.elapsedDuration - elapsedTasksDuration

        return TimerState.Ready(
            totalDuration = compiledSession.totalDuration,
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

    fun onStartSession(timerState: TimerState) {
        if (timerState is TimerState.Ready) {
            startTimerUseCase.execute(timerState.totalDuration)
        }
    }

    fun onPauseSession() {
        pauseTimerUseCase.execute()
    }

    fun onResetSession() {
        resetTimerUseCase.execute()
    }
}
