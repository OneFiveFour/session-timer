package net.onefivefour.sessiontimer.feature.sessionplayer.model

import kotlin.time.Duration

internal sealed interface TimerState {

    data class Initial(
        val totalDuration: Duration = Duration.ZERO
    ) : TimerState

    data class Active(
        val isRunning: Boolean,
        val totalDuration: Duration,
        val elapsedTotalDuration: Duration,
        val elapsedTaskDuration: Duration,
        val currentTask: UiTask?,
        val tasks: List<UiTask>
    ) : TimerState

    data object Finished : TimerState
}
