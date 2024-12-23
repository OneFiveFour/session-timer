package net.onefivefour.sessiontimer.core.timer.test.model

import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.api.model.TimerState

val FAKE_TIMER_STATUS_RUNNING = TimerState(
    TimerMode.RUNNING,
    elapsedDuration = Duration.ZERO
)

val FAKE_TIMER_STATUS_IDLE = TimerState(
    TimerMode.IDLE,
    elapsedDuration = Duration.ZERO
)

fun fakeTimerStatusFinished(totalDuration: Duration) = TimerState(
    TimerMode.FINISHED,
    elapsedDuration = totalDuration
)
