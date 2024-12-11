package net.onefivefour.sessiontimer.core.usecases.timer

import io.mockk.mockk
import io.mockk.verify
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import org.junit.Test

class PauseTimerUseCaseTest {

    private val sessionTimer: SessionTimer = mockk(relaxed = true)

    private val sut = PauseTimerUseCaseImpl(sessionTimer)

    @Test
    fun `executing UseCase is calling session timer`() {
        sut.execute()
        verify { sessionTimer.pause() }
    }
}
