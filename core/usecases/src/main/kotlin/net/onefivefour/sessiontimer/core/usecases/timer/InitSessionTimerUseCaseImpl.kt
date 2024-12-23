package net.onefivefour.sessiontimer.core.usecases.timer

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.usecases.api.timer.InitSessionTimerUseCase

@ViewModelScoped
internal class InitSessionTimerUseCaseImpl @Inject constructor(
    private val sessionTimer: SessionTimer
) : InitSessionTimerUseCase {

    override fun execute(totalDuration: Duration) {
        return sessionTimer.init(totalDuration)
    }
}
