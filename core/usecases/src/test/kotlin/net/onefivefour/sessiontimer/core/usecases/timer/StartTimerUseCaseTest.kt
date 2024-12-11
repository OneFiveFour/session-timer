package net.onefivefour.sessiontimer.core.usecases.timer

import io.mockk.mockk
import io.mockk.verify
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import org.junit.Test

class StartTimerUseCaseTest {

    private val sessionTimer: SessionTimer = mockk(relaxed = true)

    private fun sut() = StartTimerUseCaseImpl(
        sessionTimer
    )

    @Test
    fun `GIVEN a duration WHEN executing the UseCase with it THEN it is calling start on the sessionTimer`() {
        // GIVEN
        val totalDuration = Duration.ZERO

        // WHEN
        sut().execute(totalDuration)

        // THEN
        verify { sessionTimer.start(totalDuration) }
    }
}
