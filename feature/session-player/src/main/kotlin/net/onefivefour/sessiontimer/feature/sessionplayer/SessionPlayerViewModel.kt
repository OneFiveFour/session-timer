package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.usecases.session.GetCompiledSessionUseCase_Factory
import net.onefivefour.sessiontimer.core.usecases.session.GetFullSessionUseCase
import javax.inject.Inject
import kotlin.time.Duration

@HiltViewModel
internal class SessionPlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getFullSessionUseCase: GetFullSessionUseCase
) : ViewModel() {

    private val sessionId = checkNotNull(savedStateHandle.get<Long>(NAV_ARG_SESSION_ID))

    private var _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        viewModelScope.launch {
            getFullSessionUseCase.execute(sessionId).collectLatest { fullSession ->
                _uiState.update {
                    when (fullSession) {
                        null -> UiState.Error("Could not find a session with id $sessionId")
                        else -> {
                            val compiledSession = fullSession.toCompiledSession()
                            UiState.Success(
                                session = compiledSession,
                                currentTaskId = compiledSession.taskGroups.first().tasks.first().id
                            )
                        }
                    }
                }
            }
        }
    }

    fun onStartSession() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                updateWhenReady {
                    copy(currentPlayerState = SessionPlayerState.PLAYING)
                }
                delay(1000)
                updateWhenReady {
                    copy(elapsedSeconds = this.elapsedSeconds + 1)
                }
            }
        }
    }

    fun onPauseSession() {
        updateWhenReady {
            copy(currentPlayerState = SessionPlayerState.PAUSED)
        }
        timerJob?.cancel()
    }

    fun onResetSession() {
        updateWhenReady {
            copy(
                currentPlayerState = SessionPlayerState.IDLE,
                elapsedSeconds = 0
            )
        }
        timerJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    private fun updateWhenReady(newState: UiState.Success.() -> UiState) {
        val currentUiState = _uiState.value
        if (currentUiState is UiState.Success) {
            _uiState.update {
                newState(currentUiState)
            }
        }
    }
}