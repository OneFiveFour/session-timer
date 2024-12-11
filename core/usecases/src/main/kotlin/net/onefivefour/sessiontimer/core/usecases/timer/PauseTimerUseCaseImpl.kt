package net.onefivefour.sessiontimer.core.usecases.timer

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.usecases.api.timer.PauseTimerUseCase

@ViewModelScoped
internal class PauseTimerUseCaseImpl @Inject constructor(
    private val sessionTimer: SessionTimer
) : PauseTimerUseCase {

    override fun execute() {
        sessionTimer.pause()
    }
}
