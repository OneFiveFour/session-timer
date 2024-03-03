package net.onefivefour.sessiontimer.core.usecases.timer

import io.mockk.mockk
import io.mockk.verify
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.time.Duration

class StartTimerUseCaseTest {

    private val sessionTimer : SessionTimer = mockk(relaxed = true)

    private val sut = StartTimerUseCase(sessionTimer)

    @Test
    fun `executing UseCase is calling session timer`() {
        val totalDuration = Duration.ZERO
        sut.execute(totalDuration)
        verify { sessionTimer.start(totalDuration) }
    }
}