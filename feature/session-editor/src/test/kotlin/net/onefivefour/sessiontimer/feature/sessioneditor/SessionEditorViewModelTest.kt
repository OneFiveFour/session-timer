package net.onefivefour.sessiontimer.feature.sessioneditor

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.onefivefour.sessiontimer.core.database.domain.model.Session
import net.onefivefour.sessiontimer.core.usecases.DeleteTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.DeleteTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.GetFullSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.NewTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.NewTaskUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SessionEditorViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val savedStateHandleFake = SavedStateHandle()

    private val getFullSessionUseCaseMock: GetFullSessionUseCase = mockk()
    private val newTaskGroupUseCaseMock: NewTaskGroupUseCase = mockk()
    private val newTaskUseCaseMock : NewTaskUseCase = mockk()
    private val deleteTaskUseCaseMock : DeleteTaskUseCase = mockk()
    private val deleteTaskGroupUseCaseMock : DeleteTaskGroupUseCase = mockk()

    @BeforeEach
    fun setup() {
        setTestDispatcher()
    }

    @AfterEach
    fun teardown() {
        unsetTestDispatcher()
    }

    private fun setTestDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    private fun unsetTestDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() =  runTest {
        coEvery { getFullSessionUseCaseMock.execute(any()) } returns flowOf(
            Session(1L, "Session 1", emptyList())
        )
        savedStateHandleFake["sessionId"] = 1L

        val sut = createSut()

        sut.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState is UiState.Initial).isTrue()
        }

    }

    @Test
    fun `useCase is executed on init`() =  runTest {
        val sessionId = 1L
        coEvery { getFullSessionUseCaseMock.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        savedStateHandleFake["sessionId"] = sessionId

        val sut = createSut()

        advanceUntilIdle()

        coVerify(exactly = 1) { getFullSessionUseCaseMock.execute(sessionId) }

        sut.uiState.test {
            val uiState = awaitItem()
            check(uiState is UiState.Success)
            val session = uiState.session
            assertThat(session.id).isEqualTo(sessionId)
            assertThat(session.title).isEqualTo("Session 1")
            assertThat(session.taskGroups).isEmpty()
        }

    }

    @Test
    fun `newTaskGroup executes NewTaskGroupUseCase`() = runTest {
        val sessionId = 1L
        coEvery { getFullSessionUseCaseMock.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { newTaskGroupUseCaseMock.execute(any()) } returns Unit

        savedStateHandleFake["sessionId"] = sessionId

        val sut = createSut()

        sut.newTaskGroup()

        advanceUntilIdle()

        coVerify(exactly = 1) {
            newTaskGroupUseCaseMock.execute(sessionId)
        }
    }

    @Test
    fun `deleteTaskGroup executes DeleteTaskGroupUseCase`() = runTest {
        val sessionId = 1L
        val taskGroupId = 2L
        coEvery { getFullSessionUseCaseMock.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { deleteTaskGroupUseCaseMock.execute(any()) } returns Unit

        savedStateHandleFake["sessionId"] = sessionId

        val sut = createSut()

        sut.deleteTaskGroup(taskGroupId)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            deleteTaskGroupUseCaseMock.execute(taskGroupId)
        }
    }

    @Test
    fun `newTask executes NewTaskUseCase`() = runTest {
        val sessionId = 1L
        val taskGroupId = 2L
        coEvery { getFullSessionUseCaseMock.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { newTaskUseCaseMock.execute(any()) } returns Unit

        savedStateHandleFake["sessionId"] = sessionId

        val sut = createSut()

        sut.newTask(taskGroupId)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            newTaskUseCaseMock.execute(taskGroupId)
        }
    }

    @Test
    fun `deleteTask executes DeleteTaskUseCase`() = runTest {
        val sessionId = 1L
        val taskId = 2L
        coEvery { getFullSessionUseCaseMock.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { deleteTaskUseCaseMock.execute(any()) } returns Unit

        savedStateHandleFake["sessionId"] = sessionId

        val sut = createSut()

        sut.deleteTask(taskId)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            deleteTaskUseCaseMock.execute(taskId)
        }
    }

    private fun createSut() = SessionEditorViewModel(
        savedStateHandleFake,
        getFullSessionUseCaseMock,
        newTaskGroupUseCaseMock,
        newTaskUseCaseMock,
        deleteTaskUseCaseMock,
        deleteTaskGroupUseCaseMock
    )
}