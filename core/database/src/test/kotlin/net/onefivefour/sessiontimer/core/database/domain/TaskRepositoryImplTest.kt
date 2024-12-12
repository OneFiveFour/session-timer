package net.onefivefour.sessiontimer.core.database.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.Task as DatabaseTask
import net.onefivefour.sessiontimer.core.database.data.TaskDataSource
import org.junit.Test

internal class TaskRepositoryImplTest {

    private val taskDataSource: TaskDataSource = mockk()

    private fun sut() = TaskRepositoryImpl(
        taskDataSource
    )

    @Test
    fun `GIVEN task data WHEN newTask is called THEN the call is delegated to taskDataSource`() =
        runTest {
            // GIVEN
            val title = "Sample Task"
            val durationInSeconds = 300
            val taskGroupId = 1L
            coEvery { taskDataSource.insert(any(), any(), any()) } returns Unit

            // WHEN
            sut().newTask(title, durationInSeconds, taskGroupId)

            // THEN
            coVerify(exactly = 1) {
                taskDataSource.insert(
                    title = title,
                    durationInSeconds = durationInSeconds.toLong(),
                    taskGroupId = taskGroupId
                )
            }
        }

    @Test
    fun `GIVEN a list of taskGroupIds WHEN getTasksByTaskGroupIds is called THEN the mapped DomainTaskGroups should be returned`() =
        runTest {
            // GIVEN
            val taskGroupIds = listOf(1L, 2L, 3L)
            val databaseTasks = listOf(
                DatabaseTask(1L, "Task 1", 300, 1L),
                DatabaseTask(2L, "Task 2", 600, 1L),
                DatabaseTask(3L, "Task 3", 450, 2L)
            )
            coEvery { taskDataSource.getByTaskGroupIds(taskGroupIds) } returns flowOf(databaseTasks)

            // WHEN
            val tasks = sut().getTasksByTaskGroupIds(taskGroupIds)

            // THEN
            tasks.test {
                val result = awaitItem()
                assertThat(result).isEqualTo(databaseTasks.map { it.toDomainTask() })
                awaitComplete()
            }
        }

    @Test
    fun `GIVEN task data WHEN updateTask is called THEN the call is delegated to taskDataSource`() =
        runTest {
            // GIVEN
            val taskId = 1L
            val title = "Updated Task"
            val duration = 5.minutes
            coEvery { taskDataSource.update(any(), any(), any()) } returns Unit

            // WHEN
            sut().updateTask(taskId, title, duration)

            // THEN
            coVerify(exactly = 1) {
                taskDataSource.update(taskId, title, duration.toLong(DurationUnit.SECONDS))
            }
        }

    @Test
    fun `GIVEN a taskId WHEN deleteTaskById is called THEN the call is delegated to taskDataSource`() =
        runTest {
            // GIVEN
            val taskId = 1L
            coEvery { taskDataSource.deleteById(any()) } returns Unit

            // WHEN
            sut().deleteTask(taskId)

            // THEN
            coVerify(exactly = 1) { taskDataSource.deleteById(taskId) }
        }

    @Test
    fun `GIVEN a taskGroupId WHEN deleteTaskByTaskGroupId is called THEN the call is delegated to taskDataSource`() =
        runTest {
            // GIVEN
            val taskGroupId = 1L
            coEvery { taskDataSource.deleteByTaskGroupId(any()) } returns Unit

            // WHEN
            sut().deleteTasksByTaskGroupId(taskGroupId)

            // THEN
            coVerify(exactly = 1) { taskDataSource.deleteByTaskGroupId(taskGroupId) }
        }
}
