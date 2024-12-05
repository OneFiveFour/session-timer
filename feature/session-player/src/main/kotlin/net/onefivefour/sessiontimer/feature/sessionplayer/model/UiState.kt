package net.onefivefour.sessiontimer.feature.sessionplayer.model

import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import kotlin.time.Duration

internal sealed interface UiState {

    data object Initial : UiState

    data class Running(
        val sessionTitle: String,
        val currentTask: UiCompiledTask,
        val timerMode: TimerMode,
        val elapsedDuration: Duration,
        val totalDuration: Duration
    ) : UiState

    data class Error(val message: String) : UiState

    data object Finished : UiState
}
