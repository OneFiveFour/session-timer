package net.onefivefour.sessiontimer.core.usecases.timer

import io.mockk.mockk
import io.mockk.verify
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import org.junit.Test


class StartTimerUseCaseTest {

    private val sessionTimer: SessionTimer = mockk(relaxed = true)

    private val sut = StartTimerUseCaseImpl(sessionTimer)

    @Test
    fun `executing UseCase is calling session timer`() {
        val totalDuration = Duration.ZERO
        sut.execute(totalDuration)
        verify { sessionTimer.start(totalDuration) }
    }
}
