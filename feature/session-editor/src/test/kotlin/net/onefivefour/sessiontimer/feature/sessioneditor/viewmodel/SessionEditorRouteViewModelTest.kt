package net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.test.SavedStateHandleRule
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.DeleteTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.NewTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.api.task.UpdateTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.DeleteTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.NewTaskGroupUseCase
import net.onefivefour.sessiontimer.feature.sessioneditor.api.SessionEditorRoute
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class SessionEditorRouteViewModelTest {

    private val route = SessionEditorRoute(sessionId = 1L)

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    @get:Rule
    val savedStateHandleRule = SavedStateHandleRule(route)

    private val getSessionUseCase: GetSessionUseCase = mockk()
    private val newTaskGroupUseCase: NewTaskGroupUseCase = mockk()
    private val newTaskUseCase: NewTaskUseCase = mockk()
    private val deleteTaskUseCase: DeleteTaskUseCase = mockk()
    private val deleteTaskGroupUseCase: DeleteTaskGroupUseCase = mockk()
    private val updateTaskUseCase: UpdateTaskUseCase = mockk()

    private fun sut() = SessionEditorViewModel(
        savedStateHandleRule.savedStateHandleMock,
        getSessionUseCase,
        newTaskGroupUseCase,
        newTaskUseCase,
        deleteTaskUseCase,
        deleteTaskGroupUseCase,
        updateTaskUseCase
    )

    @Test
    fun `initial state is correct`() = runTest {
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(
            Session(1L, "Session 1", emptyList())
        )

        val sut = sut()

        sut.uiState.test {
            val uiState1 = awaitItem()
            assertThat(uiState1 is UiState.Initial).isTrue()
        }
    }

    @Test
    fun `useCase is executed on init`() = runTest {
        val sessionId = 1L
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )

        val sut = sut()

        advanceUntilIdle()

        coVerify(exactly = 1) { getSessionUseCase.execute(sessionId) }

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
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { newTaskGroupUseCase.execute(any()) } returns Unit

        val sut = sut()

        sut.newTaskGroup()

        advanceUntilIdle()

        coVerify(exactly = 1) {
            newTaskGroupUseCase.execute(sessionId)
        }
    }

    @Test
    fun `deleteTaskGroup executes DeleteTaskGroupUseCase`() = runTest {
        val sessionId = 1L
        val taskGroupId = 2L
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { deleteTaskGroupUseCase.execute(any()) } returns Unit

        val sut = sut()

        sut.deleteTaskGroup(taskGroupId)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            deleteTaskGroupUseCase.execute(taskGroupId)
        }
    }

    @Test
    fun `newTask executes NewTaskUseCase`() = runTest {
        val sessionId = 1L
        val taskGroupId = 2L
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { newTaskUseCase.execute(any()) } returns Unit

        val sut = sut()

        sut.newTask(taskGroupId)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            newTaskUseCase.execute(taskGroupId)
        }
    }

    @Test
    fun `deleteTask executes DeleteTaskUseCase`() = runTest {
        val sessionId = 1L
        val taskId = 2L
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { deleteTaskUseCase.execute(any()) } returns Unit

        val sut = sut()

        sut.deleteTask(taskId)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            deleteTaskUseCase.execute(taskId)
        }
    }

    @Test
    fun `updateTask executes updateTaskUseCase`() = runTest {
        val sessionId = 1L
        val duration = 3.seconds
        val title = "Task Title"
        val taskId = 1L
        coEvery { getSessionUseCase.execute(any()) } returns flowOf(
            Session(sessionId, "Session 1", emptyList())
        )
        coEvery { updateTaskUseCase.execute(any(), any(), any()) } returns Unit

        val sut = sut()
        val uiTask = UiTask(
            taskId,
            title,
            duration
        )
        sut.updateTask(uiTask)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            updateTaskUseCase.execute(taskId, title, duration)
        }
    }
}
