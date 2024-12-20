package net.onefivefour.sessiontimer.core.usecases.api.timer

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.timer.api.model.TimerState

interface GetTimerStatusUseCase {
    fun execute(): Flow<TimerState>
}
