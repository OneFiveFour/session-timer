package net.onefivefour.sessiontimer.feature.sessionplayer.model

import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode

internal sealed interface UiState {
    data object Initial : UiState

    data class Success(
        val session: CompiledSession,
        val currentTaskIndices: Int,
        val currentTask: UiTask,
        val timerMode: TimerMode,
        val elapsedSeconds: Int
    ) : UiState

    data class Error(val message: String) : UiState
}