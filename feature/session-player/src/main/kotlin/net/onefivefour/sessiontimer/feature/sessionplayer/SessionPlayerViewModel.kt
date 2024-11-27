package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus
import net.onefivefour.sessiontimer.core.usecases.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.GetTimerStatusUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.domain.TaskOrchestrator
import net.onefivefour.sessiontimer.feature.sessionplayer.domain.TaskOrchestratorImpl
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import net.onefivefour.sessiontimer.feature.sessionplayer.model.toUiSession
import net.onefivefour.sessiontimer.feature.sessionplayer.model.toUiTask

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

    private lateinit var taskOrchestrator: TaskOrchestrator

    private var _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getSessionUseCase.execute(sessionId),
                getTimerStatusUseCase.execute()
            ) { session, timerStatus ->
                when {
                    session == null -> UiState.Error("Could not find a session with id $sessionId")
                    session.taskGroups.isEmpty() -> UiState.Error("Session has no task groups")
                    session.taskGroups.all { it.tasks.isEmpty() } -> UiState.Error("Session has no tasks")
                    else -> computeUiState(session, timerStatus)
                }
            }
                .collectLatest { uiState ->
                    _uiState.update { uiState }
                }
        }
    }

    private fun computeUiState(
        session: Session,
        timerStatus: TimerStatus
    ): UiState {

        val uiSession = session.toUiSession()

        if (!::taskOrchestrator.isInitialized) {
            taskOrchestrator = TaskOrchestratorImpl(session.taskGroups)
        }

        val elapsedDuration = timerStatus.elapsedDuration
        var currentTask = taskOrchestrator.getCurrentTask()
        val durationOfFinishedTasks = taskOrchestrator.getDurationOfFinishedTasks()

        // check if current task is finished
        if (elapsedDuration > durationOfFinishedTasks + currentTask.duration) {
            taskOrchestrator.onCurrentTaskFinished()
            currentTask = taskOrchestrator.getNextTask() ?: return UiState.Finished
        }

        return UiState.Running(
            uiSession = uiSession,
            currentUiTask = currentTask.toUiTask(),
            timerMode = timerStatus.mode,
            elapsedDuration = elapsedDuration
        )
    }

    fun onStartSession() {
        doWhenSuccess {
            startTimerUseCase.execute(this.uiSession.totalDuration)
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
