package net.onefivefour.sessiontimer.core.timer.api.model

data class TimerStatus(
    val mode: TimerMode = TimerMode.IDLE,
    val elapsedSeconds: Int = 0
)

