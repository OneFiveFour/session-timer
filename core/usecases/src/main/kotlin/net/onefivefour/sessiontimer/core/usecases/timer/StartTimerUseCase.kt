package net.onefivefour.sessiontimer.core.usecases.timer

import javax.inject.Inject
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer

class StartTimerUseCase @Inject constructor(
    private val sessionTimer: SessionTimer
) {

    fun execute(totalDuration: Duration) {
        sessionTimer.start(totalDuration)
    }
}
