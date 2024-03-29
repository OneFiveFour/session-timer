package net.onefivefour.sessiontimer.core.usecases.timer

import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import javax.inject.Inject
import kotlin.time.Duration

class StartTimerUseCase @Inject constructor(
    private val sessionTimer: SessionTimer
){

    fun execute(totalDuration: Duration) {
        sessionTimer.start(totalDuration)
    }
}