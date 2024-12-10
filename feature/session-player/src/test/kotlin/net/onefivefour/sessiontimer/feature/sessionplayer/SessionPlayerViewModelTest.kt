package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.FAKE_SESSION
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.test.SavedStateHandleRule
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.timer.test.model.FAKE_TIMER_STATUS_RUNNING
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.GetTimerStatusUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.test.GetTimerStatusUseCaseFake
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class)
class SessionPlayerViewModelTest {

    private val route = SessionPlayerRoute(sessionId = 1L)

    @get:Rule(order = 1)
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 2)
    val standardTestDispatcher = StandardTestDispatcherRule()

    @get:Rule(order = 3)
    val savedStateHandleRule = SavedStateHandleRule(route)

    private val getTimerStatusUseCase = GetTimerStatusUseCaseFake()

    private val getSessionUseCase: GetSessionUseCase = mockk(relaxed = true)
    private val startTimerUseCase: StartTimerUseCase = mockk(relaxed = true)
    private val pauseTimerUseCase: PauseTimerUseCase = mockk(relaxed = true)
    private val resetTimerUseCase: ResetTimerUseCase = mockk(relaxed = true)

    @Test
    fun `initial state is UiState Initial when no session found`() = runTest {
        // Arrange
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(null)

        // Act
        val sut = sut()
        advanceUntilIdle()

        // Assert
        sut.uiState.test {
            assertThat(awaitItem()).isInstanceOf(UiState.Initial::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state becomes Error when session has no tasks`() = runTest {
        // Arrange
        val fakeSessionWithoutTasks = FAKE_SESSION.copy(taskGroups = emptyList())
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(fakeSessionWithoutTasks)

        // Act
        val sut = sut()
        advanceUntilIdle()

        // Assert
        sut.uiState.test {
            val state = awaitItem()
            assertThat(state).isInstanceOf(UiState.Error::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `session runs while timer is withing totalDuration`() = runTest {
        // Arrange
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(FAKE_SESSION)

        // Act
        val sut = sut()
        runCurrent()

        // Assert
        sut.uiState.test {
            val firstState = awaitItem()
            assertThat(firstState).isInstanceOf(UiState.Running::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `session finishes when timer surpasses totalDuration`() = runTest {
        // Arrange
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(FAKE_SESSION)
        val sessionDuration = FAKE_SESSION.taskGroups
            .flatMap { it.tasks }
            .map { it.duration }
            .fold(Duration.ZERO) { acc, duration -> acc + duration }
        getTimerStatusUseCase.update(FAKE_TIMER_STATUS_RUNNING
            .copy(elapsedDuration = sessionDuration))

        // Act
        val sut = sut()
        runCurrent()

        // Assert
        sut.uiState.test {
            val firstState = awaitItem()
            assertThat(firstState).isInstanceOf(UiState.Finished::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `onStartSession calls startTimerUseCase when in running state`() = runTest {
        // Arrange
        coEvery { getSessionUseCase.execute(1L) } returns flowOf(FAKE_SESSION)
        getTimerStatusUseCase.update(FAKE_TIMER_STATUS_RUNNING)

        // Act
        val sut = sut()
        advanceUntilIdle()
        sut.onStartSession()

        // Assert
        coVerify { startTimerUseCase.execute(any()) }
    }

    @Test
    fun `onPauseSession calls pauseTimerUseCase`() {
        // Act
        val sut = sut()
        sut.onPauseSession()

        // Assert
        coVerify { pauseTimerUseCase.execute() }
    }

    @Test
    fun `onResetSession calls resetTimerUseCase`() {
        // Act
        val sut = sut()
        sut.onResetSession()

        // Assert
        coVerify { resetTimerUseCase.execute() }
    }

    private fun sut(): SessionPlayerViewModel {
        return SessionPlayerViewModel(
            savedStateHandleRule.savedStateHandleMock,
            getSessionUseCase,
            getTimerStatusUseCase,
            startTimerUseCase,
            pauseTimerUseCase,
            resetTimerUseCase
        )
    }
}