package net.onefivefour.sessiontimer.core.timer

import com.google.common.truth.Truth.assertThat
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SessionTimerImplTest {

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private fun sut() = SessionTimerImpl(
        standardTestDispatcherRule.testDispatcher
    )

    @Test
    fun `start starts the timer`() = runTest {
        val totalDuration = 5.seconds

        val sut = sut()
        sut.start(totalDuration)

        delay(6000)
        val status = sut.getStatus().first()
        assertThat(status.mode).isEqualTo(TimerMode.FINISHED)
        assertThat(status.elapsedSeconds >= totalDuration.inWholeSeconds).isTrue()
    }

    @Test
    fun `pause pauses the timer`() = runTest {
        val sut = sut()
        sut.start(10.seconds)

        sut.pause()

        delay(5000)
        val status = sut.getStatus().first()
        assertThat(status.mode).isEqualTo(TimerMode.PAUSED)
        assertThat(status.elapsedSeconds < 10).isTrue()
    }

    @Test
    fun `reset resets the timer`() = runTest {
        val sut = sut()
        sut.start(10.seconds)
        delay(5000)

        sut.reset()

        val status = sut.getStatus().first()
        assertThat(status.mode).isEqualTo(TimerMode.IDLE)
        assertThat(status.elapsedSeconds).isEqualTo(0)
    }

    @Test
    fun `getStatus reflects updates`() = runTest {
        val totalDuration = 5.seconds
        val sut = sut()
        sut.start(totalDuration)

        delay(3000)

        val status1 = sut.getStatus().first()
        assertThat(status1.mode).isEqualTo(TimerMode.RUNNING)
        assertThat(status1.elapsedSeconds > 0).isTrue()

        delay(3000)

        val status2 = sut.getStatus().first()
        assertThat(TimerMode.FINISHED).isEqualTo(status2.mode)
        assertThat(status2.elapsedSeconds >= totalDuration.inWholeSeconds)
    }
}
