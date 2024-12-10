package net.onefivefour.sessiontimer.core.timer.test.model

import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus
import kotlin.time.Duration

val FAKE_TIMER_STATUS_RUNNING = TimerStatus(
    TimerMode.RUNNING,
    elapsedDuration = Duration.ZERO
)