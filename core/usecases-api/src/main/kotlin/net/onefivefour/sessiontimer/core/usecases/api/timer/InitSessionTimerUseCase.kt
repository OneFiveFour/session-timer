package net.onefivefour.sessiontimer.core.usecases.api.timer

import kotlin.time.Duration

interface InitSessionTimerUseCase {
    fun execute(totalDuration: Duration)
}
