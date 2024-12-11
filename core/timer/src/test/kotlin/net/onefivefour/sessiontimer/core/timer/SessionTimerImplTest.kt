package net.onefivefour.sessiontimer.core.timer

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SessionTimerImplTest {

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private fun sut() = SessionTimerImpl(
        standardTestDispatcherRule.testDispatcher
    )

    @Test
    fun `GIVEN a total duration of 5 seconds WHEN start is called THEN the timer finishes after 5 seconds`() = runTest {
        // GIVEN
        val totalDuration = 5.seconds

        // WHEN
        val sut = sut()
        sut.start(totalDuration)
        delay(6000)

        // THEN
        val status = sut.getStatus().first()
        assertThat(status.mode).isEqualTo(TimerMode.FINISHED)
        assertThat(status.elapsedDuration >= totalDuration).isTrue()
    }

    @Test
    fun `GIVEN a timer for 10 seconds WHEN pause is called THEN the timer pauses does not continue`() = runTest {
        // GIVEN
        val sut = sut()
        sut.start(10.seconds)

        // WHEN
        delay(2_100)
        sut.pause()
        delay(5_000)
        advanceUntilIdle()

        // THEN
        val status = sut.getStatus().first()
        assertThat(status.mode).isEqualTo(TimerMode.PAUSED)
        assertThat(status.elapsedDuration).isEqualTo(2.seconds)
    }

    @Test
    fun `GIVEN a timer for 10 seconds WHEN reset is called after 5 seconds THEN the timer is reset`() = runTest {
        // GIVEN
        val sut = sut()
        sut.start(10.seconds)

        // WHEN
        delay(5000)
        sut.reset()

        // THEN
        val status = sut.getStatus().first()
        assertThat(status.mode).isEqualTo(TimerMode.IDLE)
        assertThat(status.elapsedDuration).isEqualTo(0.seconds)
    }

    @Test
    fun `GIVEN a timer for 5 seconds WHEN getStatus is called THEN the result reflects updates`() = runTest {
        // GIVEN
        val totalDuration = 5.seconds
        val sut = sut()
        sut.start(totalDuration)

        // WHEN
        delay(3000)

        // THEN
        val status1 = sut.getStatus().first()
        assertThat(status1.mode).isEqualTo(TimerMode.RUNNING)
        assertThat(status1.elapsedDuration > Duration.ZERO).isTrue()

        // WHEN
        delay(3000)

        // THEN
        val status2 = sut.getStatus().first()
        assertThat(TimerMode.FINISHED).isEqualTo(status2.mode)
        assertThat(status2.elapsedDuration >= totalDuration)
    }
}
