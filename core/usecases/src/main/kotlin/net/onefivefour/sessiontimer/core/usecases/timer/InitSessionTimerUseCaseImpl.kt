package net.onefivefour.sessiontimer.core.usecases.timer

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus
import net.onefivefour.sessiontimer.core.usecases.api.timer.GetTimerStatusUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.InitSessionTimerUseCase
import kotlin.time.Duration

@ViewModelScoped
internal class InitSessionTimerUseCaseImpl @Inject constructor(
    private val sessionTimer: SessionTimer
) : InitSessionTimerUseCase {

    override fun execute(totalDuration: Duration) {
        return sessionTimer.init(totalDuration)
    }
}
