package net.onefivefour.sessiontimer.core.usecases.timer

import io.mockk.mockk
import io.mockk.verify
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import org.junit.Test

class ResetTimerUseCaseTest {

    private val sessionTimer: SessionTimer = mockk(relaxed = true)

    private fun sut() = ResetTimerUseCaseImpl(
        sessionTimer
    )

    @Test
    fun `WHEN executing the UseCase THEN it is calling reset on the sessionTimer`() {
        // WHEN
        sut().execute()

        // THEN
        verify { sessionTimer.reset() }
    }
}
