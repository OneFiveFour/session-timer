package net.onefivefour.sessiontimer.core.timer.api.model

import kotlin.time.Duration

data class TimerStatus(
    val mode: TimerMode = TimerMode.IDLE,
    val elapsedDuration: Duration = Duration.ZERO
)
