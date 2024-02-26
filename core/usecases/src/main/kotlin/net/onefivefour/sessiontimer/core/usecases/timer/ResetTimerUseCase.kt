package net.onefivefour.sessiontimer.core.usecases.timer

import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import javax.inject.Inject

class ResetTimerUseCase @Inject constructor(
    private val sessionTimer: SessionTimer
){

    fun execute() {
        sessionTimer.reset()
    }
}