package net.onefivefour.sessiontimer.core.usecases.timer

import javax.inject.Inject
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer

class PauseTimerUseCase @Inject constructor(
    private val sessionTimer: SessionTimer
) {

    fun execute() {
        sessionTimer.pause()
    }
}
