package net.onefivefour.sessiontimer.feature.sessionplayer

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.FAKE_SESSION
import net.onefivefour.sessiontimer.core.test.SavedStateHandleRule
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.test.model.FAKE_TIMER_STATUS_IDLE
import net.onefivefour.sessiontimer.core.timer.test.model.FAKE_TIMER_STATUS_RUNNING
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.SeekTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.test.GetTimerStatusUseCaseFake
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.model.TimerState
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SessionTimerViewModelTest {

    private val route = SessionPlayerRoute(sessionId = 1L)

    @get:Rule(order = 0)
    val standardTestDispatcher = StandardTestDispatcherRule()

    @get:Rule(order = 1)
    val savedStateHandleRule = SavedStateHandleRule(route)

    private val getTimerStatusUseCase = GetTimerStatusUseCaseFake()

    private val getSessionUseCase: GetSessionUseCase = mockk(relaxed = true)

    private val startTimerUseCase: StartTimerUseCase = mockk(relaxed = true)

    private val pauseTimerUseCase: PauseTimerUseCase = mockk(relaxed = true)

    private val resetTimerUseCase: ResetTimerUseCase = mockk(relaxed = true)

    private val seekTimerUseCase: SeekTimerUseCase = mockk(relaxed = true)

    private fun sut(): SessionTimerViewModel {
        return SessionTimerViewModel(
            savedStateHandleRule.savedStateHandleMock,
            getSessionUseCase,
            getTimerStatusUseCase,
            startTimerUseCase,
            pauseTimerUseCase,
            resetTimerUseCase,
            seekTimerUseCase
        )
    }

    @Test
    fun `GIVEN no available session WHEN sut is initialized THEN the initial state is TimerState Initial`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(null)

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            // THEN
            sut.timerState.test {
                assertThat(awaitItem()).isInstanceOf(TimerState.Initial::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN a normal session WHEN timer is within totalDuration THEN the timerState is Ready`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(FAKE_SESSION)
            val sessionDuration = FAKE_SESSION.taskGroups
                .flatMap { it.tasks }
                .map { it.duration }
                .fold(Duration.ZERO) { acc, duration -> acc + duration }

            // WHEN
            val sut = sut()
            getTimerStatusUseCase.update(
                FAKE_TIMER_STATUS_RUNNING.copy(elapsedDuration = sessionDuration / 2)
            )
            advanceUntilIdle()

            // THEN
            sut.timerState.test {
                val firstState = awaitItem()
                assertThat(firstState).isInstanceOf(TimerState.Active::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN a normal session WHEN the timer finishes THEN the timerState is Finished`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(FAKE_SESSION)

            // WHEN
            val sut = sut()
            getTimerStatusUseCase.update(
                FAKE_TIMER_STATUS_RUNNING.copy(mode = TimerMode.FINISHED)
            )
            advanceUntilIdle()

            // THEN
            sut.timerState.test {
                val firstState = awaitItem()
                assertThat(firstState).isInstanceOf(TimerState.Finished::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN an idle timerStatus WHEN onStartSession is called THEN startTimerUseCase is executed`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(1L) } returns flowOf(FAKE_SESSION)
            getTimerStatusUseCase.update(FAKE_TIMER_STATUS_IDLE)

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            sut.onStartSession()
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) { startTimerUseCase.execute(any()) }
        }

    @Test
    fun `GIVEN an initialized sut WHEN onPauseSession is called THEN pauseTimerUseCase is executed`() =
        runTest {
            // GIVEN
            val sut = sut()

            // WHEN
            sut.onPauseSession()
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) { pauseTimerUseCase.execute() }
        }

    @Test
    fun `GIVEN an initialized sut WHEN onResetSession is called THEN resetTimerUseCase is executed`() =
        runTest {
            // GIVEN
            val sut = sut()

            // WHEN
            sut.onResetSession()
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) { resetTimerUseCase.execute() }
        }

    @Test
    fun `GIVEN an initialized sut WHEN onNextTask is called THEN seekTimerUseCase is executed`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(FAKE_SESSION)
            val sut = sut()
            advanceUntilIdle()

            // WHEN
            sut.onNextTask()
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) { seekTimerUseCase.execute(any()) }
        }

    @Test
    fun `GIVEN an initialized sut with no session WHEN onNextTask is called THEN seekTimerUseCase is not executed`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(null)
            val sut = sut()
            advanceUntilIdle()

            // WHEN
            sut.onNextTask()
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 0) { seekTimerUseCase.execute(any()) }
        }

    @Test
    fun `GIVEN an initialized sut WHEN onPreviousTask is called THEN seekTimerUseCase is executed`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(FAKE_SESSION)
            val sut = sut()
            advanceUntilIdle()

            // WHEN
            sut.onPreviousTask()
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) { seekTimerUseCase.execute(any()) }
        }
}
