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
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.usecases.taskgroup.DeleteTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.task.DeleteTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.session.GetFullSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.taskgroup.NewTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.task.NewTaskUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SessionEditorViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val savedStateHandleFake = SavedStateHandle()

    private val getFullSessionUseCase: GetFullSessionUseCase = mockk()
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
        coEvery { getFullSessionUseCase.execute(any()) } returns flowOf(
            Session(1L, "Session 1", emptyList())
        )
        savedStateHandleFake["sessionId"] = 1L

        val sut = sut()

        sut.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState is UiState.Initial).isTrue()
        }

    }

    @Test
    fun `useCase is executed on init`() =  runTest {
        val sessionId = 1L
        coEvery { getFullSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        savedStateHandleFake["sessionId"] = sessionId

        val sut = sut()

        advanceUntilIdle()

        coVerify(exactly = 1) { getFullSessionUseCase.execute(sessionId) }

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
        coEvery { getFullSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { newTaskGroupUseCaseMock.execute(any()) } returns Unit

        savedStateHandleFake["sessionId"] = sessionId

        val sut = sut()

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
        coEvery { getFullSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { deleteTaskGroupUseCaseMock.execute(any()) } returns Unit

        savedStateHandleFake["sessionId"] = sessionId

        val sut = sut()

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
        coEvery { getFullSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { newTaskUseCaseMock.execute(any()) } returns Unit

        savedStateHandleFake["sessionId"] = sessionId

        val sut = sut()

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
        coEvery { getFullSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { deleteTaskUseCaseMock.execute(any()) } returns Unit

        savedStateHandleFake["sessionId"] = sessionId

        val sut = sut()

        sut.deleteTask(taskId)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            deleteTaskUseCaseMock.execute(taskId)
        }
    }

    private fun sut() = SessionEditorViewModel(
        savedStateHandleFake,
        getFullSessionUseCase,
        newTaskGroupUseCaseMock,
        newTaskUseCaseMock,
        deleteTaskUseCaseMock,
        deleteTaskGroupUseCaseMock
    )
}