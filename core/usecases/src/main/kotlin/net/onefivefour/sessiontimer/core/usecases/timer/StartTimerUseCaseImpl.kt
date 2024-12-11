package net.onefivefour.sessiontimer.core.usecases.timer

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.usecases.api.timer.StartTimerUseCase

@ViewModelScoped
class StartTimerUseCaseImpl @Inject constructor(
    private val sessionTimer: SessionTimer
) : StartTimerUseCase {

    override fun execute(totalDuration: Duration) {
        sessionTimer.start(totalDuration)
    }
}
