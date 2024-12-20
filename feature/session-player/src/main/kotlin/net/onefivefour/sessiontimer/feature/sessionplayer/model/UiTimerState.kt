package net.onefivefour.sessiontimer.feature.sessionplayer.model

import kotlin.time.Duration

internal sealed interface UiTimerState {

    data class Initial(
        val totalDuration: Duration = Duration.ZERO
    ) : UiTimerState

    data class Active(
        val isRunning: Boolean,
        val totalDuration: Duration,
        val elapsedTotalDuration: Duration,
        val elapsedTaskDuration: Duration,
        val currentTask: UiTask?,
        val tasks: List<UiTask>
    ) : UiTimerState

    data object Finished : UiTimerState
}
