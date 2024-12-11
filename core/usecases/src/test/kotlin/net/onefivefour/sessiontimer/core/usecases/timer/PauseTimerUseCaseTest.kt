package net.onefivefour.sessiontimer.core.usecases.timer

import io.mockk.mockk
import io.mockk.verify
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import org.junit.Test

class PauseTimerUseCaseTest {

    private val sessionTimer: SessionTimer = mockk(relaxed = true)

    private fun sut() = PauseTimerUseCaseImpl(
        sessionTimer
    )

    @Test
    fun `WHEN executing the UseCase THE it is calling pause on the sessionTimer`() {
        // WHEN
        sut().execute()

        // THEN
        verify { sessionTimer.pause() }
    }
}
