package net.onefivefour.sessiontimer.core.usecases.timer

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.usecases.api.timer.ResetTimerUseCase

@ViewModelScoped
class ResetTimerUseCaseImpl @Inject constructor(
    private val sessionTimer: SessionTimer
) : ResetTimerUseCase {

    override fun execute() {
        sessionTimer.reset()
    }
}
