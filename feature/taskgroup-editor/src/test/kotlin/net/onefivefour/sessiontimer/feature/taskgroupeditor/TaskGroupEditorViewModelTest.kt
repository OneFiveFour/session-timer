package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.usecases.taskgroup.GetTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.taskgroup.UpdateTaskGroupUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskGroupEditorViewModelTest {

    private val getTaskGroupUseCase: GetTaskGroupUseCase = mockk()
    private val updateTaskGroupUseCase: UpdateTaskGroupUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private fun sut() = TaskGroupEditorViewModel(
        getTaskGroupUseCase,
        updateTaskGroupUseCase
    )

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
    fun `uiState has correct initial value`() {
        val sut = sut()

        assertThat(sut.uiState.value).isEqualTo(UiState.Initial)
    }

    @Test
    fun `GetTaskGroupUseCase is called on init`() = runTest {
        val taskGroupId = 1L
        coEvery { getTaskGroupUseCase.execute(taskGroupId) } returns flowOf(
            TaskGroup(
                id = taskGroupId,
                title = "TaskGroup 1",
                color = 0xFFFF0000,
                playMode = PlayMode.SEQUENCE,
                numberOfRandomTasks = 3,
                tasks = emptyList(),
                sessionId = 2L
            )
        )

        val sut = sut()

        advanceUntilIdle()

        coVerify(exactly = 1) { getTaskGroupUseCase.execute(taskGroupId) }

        sut.uiState.test {
            val uiState = awaitItem()
            check(uiState is UiState.Success)
            val taskGroup = uiState.taskGroup
            assertThat(taskGroup.id).isEqualTo(taskGroupId)
            assertThat(taskGroup.title).isEqualTo("TaskGroup 1")
            assertThat(taskGroup.color).isEqualTo(Color(0xFFFF0000))
            assertThat(taskGroup.playMode).isEqualTo(PlayMode.SEQUENCE)
            assertThat(taskGroup.numberOfRandomTasks).isEqualTo(3)
            assertThat(taskGroup.tasks).isEmpty()
        }
    }

    @Test
    fun `updateTaskGroup delegates to UpdateTaskGroupUseCase`() = runTest {
        val taskGroupId = 1L
        coEvery { getTaskGroupUseCase.execute(taskGroupId) } returns flowOf(
            TaskGroup(
                id = taskGroupId,
                title = "TaskGroup 1",
                color = 0xFF0000,
                playMode = PlayMode.SEQUENCE,
                numberOfRandomTasks = 3,
                tasks = emptyList(),
                sessionId = 2L
            )
        )
        coEvery { updateTaskGroupUseCase.execute(
            any(),
            any(),
            any(),
            any(),
            any()
        ) } returns Unit

        val sut = sut()
        val title = "Test TaskGroup Title"
        val color = Color(0xFF00FF00)
        val playMode = PlayMode.RANDOM
        val numberOfRandomTasks = 5
        val uiTaskGroup = UiTaskGroup(
            taskGroupId,
            title,
            color,
            playMode,
            numberOfRandomTasks,
            emptyList()
        )
        sut.updateTaskGroup(uiTaskGroup)

        advanceUntilIdle()

        coVerify {
            updateTaskGroupUseCase.execute(
                taskGroupId,
                title,
                color.toArgb(),
                playMode,
                numberOfRandomTasks
            )
        }
    }

}