package net.onefivefour.sessiontimer.core.usecases.api.timer

import kotlin.time.Duration

interface SeekTimerUseCase {
    fun execute(totalDuration: Duration)
}
