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
import org.junit.Test

class GetTimerStatusUseCaseTest {

    private val sessionTimer: SessionTimer = mockk()

    private fun sut() = GetTimerStatusUseCaseImpl(
        sessionTimer
    )

    @Test
    fun `GIVEN a new timerStates WHEN executing the UseCase THEN it is calling getStatus on the sessionTimer`() =
        runTest {
            // GIVEN
            coEvery { sessionTimer.getStatus() } returns flowOf(
                TimerStatus()
            )

            // WHEN
            sut().execute().test {
                val initialStatus = awaitItem()
                assertThat(initialStatus.mode).isEqualTo(TimerMode.IDLE)
                awaitComplete()
            }

            // THEN
            verify { sessionTimer.getStatus() }
        }
}
