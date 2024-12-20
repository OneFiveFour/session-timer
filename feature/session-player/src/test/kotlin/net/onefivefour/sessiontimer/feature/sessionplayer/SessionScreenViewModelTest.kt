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
import net.onefivefour.sessiontimer.core.common.domain.model.getTotalDuration
import net.onefivefour.sessiontimer.core.test.SavedStateHandleRule
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.test.model.FAKE_TIMER_STATUS_IDLE
import net.onefivefour.sessiontimer.core.timer.test.model.FAKE_TIMER_STATUS_RUNNING
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.test.GetTimerStatusUseCaseFake
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.domain.SessionCompiler
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SessionScreenViewModelTest {

    private val route = SessionPlayerRoute(sessionId = 1L)

    @get:Rule(order = 0)
    val standardTestDispatcher = StandardTestDispatcherRule()

    @get:Rule(order = 1)
    val savedStateHandleRule = SavedStateHandleRule(route)

    private val sessionCompiler = SessionCompiler()

    private val getSessionUseCase: GetSessionUseCase = mockk(relaxed = true)

    private fun sut(): SessionScreenViewModel {
        return SessionScreenViewModel(
            savedStateHandle = savedStateHandleRule.savedStateHandleMock,
            sessionCompiler = sessionCompiler,
            getSessionUseCase = getSessionUseCase
        )
    }

    @Test
    fun `GIVEN no available session WHEN sut is initialized THEN the initial uiState is UiState Initial`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(null)

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            // THEN
            sut.uiState.test {
                assertThat(awaitItem()).isInstanceOf(UiState.Initial::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN a session without tasks WHEN sut is initialized THEN the uiState is Error`() =
        runTest {
            // GIVEN
            val fakeSessionWithoutTasks = FAKE_SESSION.copy(taskGroups = emptyList())
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(fakeSessionWithoutTasks)

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            // THEN
            sut.uiState.test {
                val state = awaitItem()
                assertThat(state).isInstanceOf(UiState.Error::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN a normal session WHEN sut is initialized THEN the uiState is Ready`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(FAKE_SESSION)

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            // THEN
            sut.uiState.test {
                val firstState = awaitItem()
                assertThat(firstState).isInstanceOf(UiState.Ready::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN a normal session WHEN the timer has finished THEN the uiState is Finished`() =
        runTest {
            // GIVEN
            coEvery { getSessionUseCase.execute(any()) } returns flowOf(FAKE_SESSION)

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            // THEN
            sut.uiState.test {
                val firstState = awaitItem()
                assertThat(firstState).isInstanceOf(UiState.Ready::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }
}
