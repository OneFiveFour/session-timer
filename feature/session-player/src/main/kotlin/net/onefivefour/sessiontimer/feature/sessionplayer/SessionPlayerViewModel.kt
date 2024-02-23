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
                        else -> {
                            val compiledSession = fullSession.toCompiledSession()
                            UiState.Success(
                                session = compiledSession,
                                currentTask = compiledSession.taskGroups.first().tasks.first()
                            )
                        }
                    }
                }
            }
        }
    }

    fun onStartSession() {
        cancelTimer()

        updateWhenReady {
            copy(currentPlayerState = SessionPlayerState.PLAYING)
        }

        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                updateWhenReady {
                    var newElapsedSeconds = this.elapsedSeconds + 1
                    val currentTask = if (newElapsedSeconds < this.currentTask.duration.inWholeSeconds) {
                        this.currentTask
                    } else {
                        newElapsedSeconds = 0
                        getNextTask(this.session.taskGroups, this.currentTask.id)
                    }

                    if (currentTask == null) {
                        cancelTimer()
                        copy(currentPlayerState = SessionPlayerState.FINISHED)
                    } else {
                        copy(elapsedSeconds = newElapsedSeconds, currentTask = currentTask)
                    }
                }
            }
        }
    }

    fun onPauseSession() {
        updateWhenReady {
            copy(currentPlayerState = SessionPlayerState.PAUSED)
        }
        cancelTimer()
    }

    fun onResetSession() {
        updateWhenReady {
            copy(
                currentTask = session.taskGroups.first().tasks.first(),
                currentPlayerState = SessionPlayerState.IDLE,
                elapsedSeconds = 0
            )
        }
        cancelTimer()
    }

    override fun onCleared() {
        super.onCleared()
        cancelTimer()
    }

    private fun cancelTimer() {
        timerJob?.cancel()
    }

    private fun getNextTask(taskGroups: List<UiTaskGroup>, currentTaskId: Long): UiTask? {
        for ((groupIndex, taskGroup) in taskGroups.withIndex()) {
            for ((taskIndex, task) in taskGroup.tasks.withIndex()) {
                if (task.id == currentTaskId) {
                    // Current task found
                    return if (taskIndex < taskGroup.tasks.lastIndex) {
                        // There is a next task in the same group
                        taskGroup.tasks[taskIndex + 1]
                    } else if (groupIndex < taskGroups.lastIndex) {
                        // Current task is the last in the group, but there is a next group
                        taskGroups[groupIndex + 1].tasks.firstOrNull()
                    } else {
                        // Current task is the last in the last group
                        null
                    }
                }
            }
        }
        return null  // Task with given ID not found
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