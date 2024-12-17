package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.test.SavedStateHandleRule
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.GetTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.UpdateTaskGroupUseCase
import net.onefivefour.sessiontimer.feature.taskgroupeditor.api.TaskGroupEditorRoute
import org.junit.Rule
import org.junit.Test
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class)
internal class TaskGroupEditorViewModelTest {

    private val route = TaskGroupEditorRoute(taskGroupId = 1L)

    @get:Rule(order = 0)
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    @get:Rule(order = 1)
    val savedStateHandleRule = SavedStateHandleRule(route)

    private val getTaskGroupUseCase: GetTaskGroupUseCase = mockk()

    private val updateTaskGroupUseCase: UpdateTaskGroupUseCase = mockk()

    private fun sut() = TaskGroupEditorViewModel(
        savedStateHandleRule.savedStateHandleMock,
        getTaskGroupUseCase,
        updateTaskGroupUseCase
    )

    @Test
    fun `WHEN ViewModel is created THEN its uiState has correct initial value`() {
        // WHEN
        val sut = sut()

        // THEN
        assertThat(sut.uiState.value).isEqualTo(UiState.Initial)
    }

    @Test
    fun `GIVEN a taskGroup WHEN ViewModel is created THEN GetTaskGroupUseCase is called on init`() =
        runTest {
            // GIVEN
            val taskGroupId = 1L
            coEvery { getTaskGroupUseCase.execute(taskGroupId) } returns flowOf(
                TaskGroup(
                    id = taskGroupId,
                    title = "TaskGroup 1",
                    color = 0xFFFF0000,
                    playMode = PlayMode.SEQUENCE,
                    numberOfRandomTasks = 3,
                    tasks = listOf(
                        Task(
                            id = 3L,
                            title = "Task 1",
                            duration = Duration.ZERO,
                            taskGroupId = taskGroupId
                        )
                    ),
                    sessionId = 2L
                )
            )

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            // THEN
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
                assertThat(taskGroup.tasks).hasSize(1)

                val task = taskGroup.tasks[0]
                assertThat(task.id).isEqualTo(3L)
                assertThat(task.title).isEqualTo("Task 1")
            }
        }

    @Test
    fun `GIVEN taskGroup data WHEN updateTaskGroup is called THEN UpdateTaskGroupUseCase is executed with that data`() =
        runTest {
            // GIVEN
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
            coEvery {
                updateTaskGroupUseCase.execute(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns Unit

            // WHEN
            val sut = sut()
            val title = "Test TaskGroup Title"
            val color = Color(0xFF00FF00)
            val playMode = PlayMode.RANDOM_SINGLE_TASK
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

            // THEN
            coVerify(exactly = 1) {
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
