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
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.usecases.session.GetFullSessionUseCase
import javax.inject.Inject

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
                        // TODO think about creating UI data classes. at least for now 3 differen versions
                        //   of Session (Overview, Editor and Compiled)
                        else -> UiState.Success(
                            session = fullSession,
                            currentTaskId = fullSession.taskGroups.first().tasks.first().id
                        )
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
                    copy(currentPlayMode = SessionPlayMode.PLAYING)
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
            copy(currentPlayMode = SessionPlayMode.PAUSED)
        }
        timerJob?.cancel()
    }

    fun onResetSession() {
        updateWhenReady {
            copy(
                currentPlayMode = SessionPlayMode.IDLE,
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

internal sealed interface UiState {
    data object Initial : UiState

    data class Success(
        val session: Session,
        val currentTaskId: Long,
        val currentPlayMode: SessionPlayMode = SessionPlayMode.IDLE,
        val elapsedSeconds: Int = 0
    ) : UiState

    data class Error(val message: String) : UiState
}

enum class SessionPlayMode {
    IDLE, PAUSED, PLAYING
}