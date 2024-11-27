package net.onefivefour.sessiontimer.feature.sessionplayer.model

import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import kotlin.time.Duration

internal sealed interface UiState {
    data object Initial : UiState

    data class Running(
        val uiSession: UiSession,
        val currentUiTask: UiTask,
        val timerMode: TimerMode,
        val elapsedDuration: Duration
    ) : UiState

    data class Error(val message: String) : UiState

    data object Finished : UiState
}
