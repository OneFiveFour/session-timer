package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.GetTimerStatusUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.domain.SessionCompiler
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiCompiledSession
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiCompiledTask
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import kotlin.time.Duration

@HiltViewModel
internal class SessionPlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getSessionUseCase: GetSessionUseCase,
    getTimerStatusUseCase: GetTimerStatusUseCase,
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val resetTimerUseCase: ResetTimerUseCase
) : ViewModel() {

    private val sessionId = savedStateHandle.toRoute<SessionPlayerRoute>().sessionId

    private lateinit var compiledSession: UiCompiledSession

    private var _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getSessionUseCase.execute(sessionId),
                getTimerStatusUseCase.execute()
            ) { session, timerStatus ->

                if (session == null) {
                    return@combine UiState.Initial
                }

                if (!::compiledSession.isInitialized) {
                    compiledSession = SessionCompiler.compile(session)
                }

                when {
                    compiledSession.taskList.isEmpty() -> UiState.Error("Session has no tasks")
                    else -> computeUiState(compiledSession, timerStatus)
                }
            }.collect { newUiState ->
                _uiState.update { newUiState }
            }
        }
    }

    private fun computeUiState(
        compiledSession: UiCompiledSession,
        timerStatus: TimerStatus
    ): UiState {

        val elapsedDuration = timerStatus.elapsedDuration
        val taskList = compiledSession.taskList

        val currentTask = determineCurrentTask(elapsedDuration, taskList) ?: return UiState.Finished

        return UiState.Running(
            sessionTitle = compiledSession.sessionTitle,
            currentTask = currentTask,
            timerMode = timerStatus.mode,
            elapsedDuration = elapsedDuration,
            totalDuration = compiledSession.totalDuration
        )
    }

    private fun determineCurrentTask(
        elapsedDuration: Duration,
        taskList: List<UiCompiledTask>
    ): UiCompiledTask? {
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
        doWhenSuccess {
            startTimerUseCase.execute(this.totalDuration)
        }
    }

    fun onPauseSession() {
        pauseTimerUseCase.execute()
    }

    fun onResetSession() {
        resetTimerUseCase.execute()
    }

    private fun doWhenSuccess(action: UiState.Running.() -> Unit) {
        val currentUiState = _uiState.value
        if (currentUiState is UiState.Running) {
            action(currentUiState)
        }
    }
}
