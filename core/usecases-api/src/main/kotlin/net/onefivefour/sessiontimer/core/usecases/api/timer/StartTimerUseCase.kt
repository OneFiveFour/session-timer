package net.onefivefour.sessiontimer.core.usecases.api.timer

import kotlin.time.Duration

interface StartTimerUseCase {
    fun execute(totalDuration: Duration)
}