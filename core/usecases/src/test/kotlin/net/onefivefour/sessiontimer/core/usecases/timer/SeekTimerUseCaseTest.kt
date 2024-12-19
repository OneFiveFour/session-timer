package net.onefivefour.sessiontimer.core.usecases.timer

import io.mockk.mockk
import io.mockk.verify
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import org.junit.Test

internal class SeekTimerUseCaseTest {

    private val sessionTimer: SessionTimer = mockk(relaxed = true)

    private fun sut() = SeekTimerUseCaseImpl(
        sessionTimer
    )

    @Test
    fun `GIVEN a zero duration WHEN executing the UseCase THEN it is calling seekTo on the sessionTimer`() {
        // GIVEN
        val totalDuration = Duration.ZERO

        // WHEN
        sut().execute(totalDuration)

        // THEN
        verify { sessionTimer.seekTo(totalDuration) }
    }
}
