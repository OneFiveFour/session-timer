package net.onefivefour.sessiontimer.core.usecases.timer

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus
import org.junit.jupiter.api.Test

class GetTimerStatusUseCaseTest {

    private val sessionTimer: SessionTimer = mockk()

    private val sut = GetTimerStatusUseCase(sessionTimer)

    @Test
    fun `executing UseCase is calling session timer`() = runTest {
        coEvery { sessionTimer.getStatus() } returns flowOf(
            TimerStatus()
        )

        sut.execute().test {
            val initialStatus = awaitItem()
            assertThat(initialStatus.mode).isEqualTo(TimerMode.IDLE)
            awaitComplete()
        }

        verify { sessionTimer.getStatus() }
    }
}
