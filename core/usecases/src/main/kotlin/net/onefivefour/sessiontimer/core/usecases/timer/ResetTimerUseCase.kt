package net.onefivefour.sessiontimer.core.usecases.timer

import javax.inject.Inject
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer

class ResetTimerUseCase @Inject constructor(
    private val sessionTimer: SessionTimer
) {

    fun execute() {
        sessionTimer.reset()
    }
}
