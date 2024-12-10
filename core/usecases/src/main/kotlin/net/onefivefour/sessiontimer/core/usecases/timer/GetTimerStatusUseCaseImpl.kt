package net.onefivefour.sessiontimer.core.usecases.timer

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus
import net.onefivefour.sessiontimer.core.usecases.api.timer.GetTimerStatusUseCase
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
internal class GetTimerStatusUseCaseImpl @Inject constructor(
    private val sessionTimer: SessionTimer
): GetTimerStatusUseCase {

    override fun execute(): Flow<TimerStatus> {
        return sessionTimer.getStatus()
    }
}
