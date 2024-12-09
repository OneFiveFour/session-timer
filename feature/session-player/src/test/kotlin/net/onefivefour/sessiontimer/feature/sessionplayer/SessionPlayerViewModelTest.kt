package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.test.SavedStateHandleRule
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.GetTimerStatusUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.api.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class SessionPlayerViewModelTest {

    private val route = SessionPlayerRoute(sessionId = 1L)

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 2)
    val standardTestDispatcher = StandardTestDispatcherRule()

    @get:Rule(order = 3)
    val savedStateHandleRule = SavedStateHandleRule(route)

    @Inject
    lateinit var getTimerStatusUseCase: GetTimerStatusUseCase

    @Inject
    lateinit var getSessionUseCase: GetSessionUseCase

    @Inject
    lateinit var startTimerUseCase: StartTimerUseCase

    @Inject
    lateinit var pauseTimerUseCase: PauseTimerUseCase

    @Inject
    lateinit var resetTimerUseCase: ResetTimerUseCase


    private val fakeSession = Session(
        id = 1L,
        title = "Test Session",
        taskGroups = listOf(
            TaskGroup(
                id = 2L,
                title = "Test Task Group",
                color = 0xFF0000,
                playMode = PlayMode.SEQUENCE,
                tasks = listOf(
                    Task(
                        id = 3L,
                        title = "Test Task 3L",
                        duration = 10.seconds,
                        taskGroupId = 2L
                    ),
                    Task(
                        id = 4L,
                        title = "Test Task 4L",
                        duration = 10.seconds,
                        taskGroupId = 2L
                    )
                ),
                sessionId = 1L
            )
        )
    )

    @Before
    fun setUp() {
        hiltRule.inject()
    }

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
        val fakeSessionWithoutTasks = fakeSession.copy(taskGroups = emptyList())
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
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(fakeSession)

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
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(fakeSession)

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
        val mockSession = mockk<Session> {
            coEvery { taskGroups } returns listOf(
                mockk {

                }
            )
        }

        coEvery { getSessionUseCase.execute(1L) } returns flowOf(mockSession)

        // Act
        val sut = sut()
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