package net.onefivefour.sessiontimer.core.usecases.timer

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus

class GetTimerStatusUseCase @Inject constructor(
    private val sessionTimer: SessionTimer
) {

    fun execute(): Flow<TimerStatus> {
        return sessionTimer.getStatus()
    }
}
