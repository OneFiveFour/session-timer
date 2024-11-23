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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus
import net.onefivefour.sessiontimer.core.usecases.session.GetFullSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.GetTimerStatusUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTask
import net.onefivefour.sessiontimer.feature.sessionplayer.model.toCompiledSession

@HiltViewModel
internal class SessionPlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getFullSessionUseCase: GetFullSessionUseCase,
    private val getTimerStatusUseCase: GetTimerStatusUseCase,
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val resetTimerUseCase: ResetTimerUseCase
) : ViewModel() {

    private val sessionId = savedStateHandle.toRoute<SessionPlayerRoute>().sessionId

    private var _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getFullSessionUseCase.execute(sessionId),
                getTimerStatusUseCase.execute()
            ) { fullSession, timerStatus ->
                when (fullSession) {
                    null -> UiState.Error("Could not find a session with id $sessionId")
                    else -> computeUiStateSuccess(fullSession, timerStatus)
                }
            }
                .collectLatest { uiState ->
                    _uiState.update { uiState }
                }
        }
    }

    private fun computeUiStateSuccess(
        fullSession: Session,
        timerStatus: TimerStatus
    ): UiState.Success {
        val compiledSession = fullSession.toCompiledSession()

        val elapsedSeconds = timerStatus.elapsedSeconds
        var currentTaskIndices = 0
        var currentTask: UiTask? = null
        var pastTaskDuration = Duration.ZERO

        for ((index, taskIndices) in compiledSession.taskIndices.withIndex()) {
            val (taskGroupIndex, taskIndex) = taskIndices

            val taskDuration = compiledSession
                .taskGroups[taskGroupIndex]
                .tasks[taskIndex]
                .duration

            pastTaskDuration += taskDuration

            if (pastTaskDuration.inWholeSeconds > elapsedSeconds) {
                currentTaskIndices = index
                currentTask = compiledSession
                    .taskGroups[taskGroupIndex]
                    .tasks[taskIndex]
                break
            }
        }

        return UiState.Success(
            session = compiledSession,
            currentTaskIndices = currentTaskIndices,
            currentTask = currentTask!!,
            timerMode = timerStatus.mode,
            elapsedSeconds = elapsedSeconds
        )
    }

    fun onStartSession() {
        doWhenSuccess {
            startTimerUseCase.execute(this.session.totalDuration)
        }

//        timerJob = viewModelScope.launch {
//            while (true) {
//                delay(1000)
//                updateWhenReady {
//                    var newElapsedSeconds = this.elapsedSeconds + 1
//                    val currentTask = if (newElapsedSeconds < this.currentTask.duration.inWholeSeconds) {
//                        this.currentTask
//                    } else {
//                        newElapsedSeconds = 0
//                        getNextTask(this.session.taskGroups, this.currentTask.id)
//                    }
//
//                    if (currentTask == null) {
//                        cancelTimer()
//                        copy(currentPlayerState = net.onefivefour.sessiontimer.feature.sessionplayer.model.SessionPlayerState.FINISHED)
//                    } else {
//                        copy(elapsedSeconds = newElapsedSeconds, currentTask = currentTask)
//                    }
//                }
//            }
//        }
    }

    fun onPauseSession() {
        pauseTimerUseCase.execute()
    }

    fun onResetSession() {
        resetTimerUseCase.execute()
    }

    private fun updateWhenReady(newState: UiState.Success.() -> UiState) {
        val currentUiState = _uiState.value
        if (currentUiState is UiState.Success) {
            _uiState.update {
                newState(currentUiState)
            }
        }
    }

    private fun doWhenSuccess(action: UiState.Success.() -> Unit) {
        val currentUiState = _uiState.value
        if (currentUiState is UiState.Success) {
            action(currentUiState)
        }
    }
}
