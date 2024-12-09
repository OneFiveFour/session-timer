package net.onefivefour.sessiontimer.core.usecases.timer

import javax.inject.Inject
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.usecases.api.timer.ResetTimerUseCase

class ResetTimerUseCaseImpl @Inject constructor(
    private val sessionTimer: SessionTimer
): ResetTimerUseCase {

    override fun execute() {
        sessionTimer.reset()
    }
}
