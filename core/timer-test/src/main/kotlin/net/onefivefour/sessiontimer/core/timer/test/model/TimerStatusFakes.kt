package net.onefivefour.sessiontimer.core.timer.test.model

import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus

val FAKE_TIMER_STATUS_RUNNING = TimerStatus(
    TimerMode.RUNNING,
    elapsedDuration = Duration.ZERO
)

val FAKE_TIMER_STATUS_IDLE = TimerStatus(
    TimerMode.IDLE,
    elapsedDuration = Duration.ZERO
)
