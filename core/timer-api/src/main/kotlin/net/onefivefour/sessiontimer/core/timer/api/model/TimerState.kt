package net.onefivefour.sessiontimer.core.timer.api.model

import kotlin.time.Duration

data class TimerState(
    val mode: TimerMode = TimerMode.IDLE,
    val totalDuration: Duration? = null,
    val elapsedDuration: Duration = Duration.ZERO
)
