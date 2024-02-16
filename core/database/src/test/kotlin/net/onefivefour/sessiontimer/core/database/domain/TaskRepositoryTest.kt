package net.onefivefour.sessiontimer.core.database.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.Task as DomainTask
import net.onefivefour.sessiontimer.core.database.Task as DatabaseTask
import net.onefivefour.sessiontimer.core.database.data.TaskDataSource
import org.junit.jupiter.api.Test
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

class TaskRepositoryTest {

    private val taskDataSource: TaskDataSource = mockk()

    private val sut = TaskRepository(
        taskDataSource
    )

    @Test
    fun `new task insertion should call data source insert method`() = runTest {
        val title = "Sample Task"
        val durationInSeconds = 300
        val taskGroupId = 1L

        coEvery { taskDataSource.insert(any(), any(), any()) } returns Unit

        sut.new(title, durationInSeconds, taskGroupId)

        coVerify {
            taskDataSource.insert(
                title = title,
                durationInSeconds = durationInSeconds.toLong(),
                taskGroupId = taskGroupId
            )
        }
    }

    @Test
    fun `getByTaskGroupIds should return mapped DomainTasks`() = runTest {
        val taskGroupIds = listOf(1L, 2L, 3L)
        val databaseTasks = listOf(
            DatabaseTask(1L, "Task 1", 300, 1L),
            DatabaseTask(2L, "Task 2", 600, 1L),
            DatabaseTask(3L, "Task 3", 450, 2L)
        )

        coEvery { taskDataSource.getByTaskGroupIds(taskGroupIds) } returns flowOf(databaseTasks)

        sut.getByTaskGroupIds(taskGroupIds).test {
            val result = awaitItem()
            assertThat(result).isEqualTo(databaseTasks.map { it.toDomainTask() })
            awaitComplete()
        }
    }

    @Test
    fun `update task should call data source update method`() = runTest {
        val taskId = 1L
        val title = "Updated Task"
        val duration = 5.minutes

        coEvery { taskDataSource.update(any(), any(), any()) } returns Unit

        sut.update(taskId, title, duration)

        coVerify { taskDataSource.update(taskId, title, duration.toLong(DurationUnit.SECONDS)) }
    }

    @Test
    fun `delete task should call data source deleteById method`() = runTest {
        val taskId = 1L

        coEvery { taskDataSource.deleteById(any()) } returns Unit

        sut.delete(taskId)

        coVerify { taskDataSource.deleteById(taskId) }
    }

    @Test
    fun `delete tasks by task group id should call data source deleteByTaskGroupId method`() =
        runTest {
            val taskGroupId = 1L

            coEvery { taskDataSource.deleteByTaskGroupId(any()) } returns Unit

            sut.deleteByTaskGroupId(taskGroupId)

            coVerify { taskDataSource.deleteByTaskGroupId(taskGroupId) }
        }
}
