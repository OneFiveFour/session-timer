package net.onefivefour.sessiontimer.core.database.domain

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.Task as DatabaseTask
import net.onefivefour.sessiontimer.core.database.data.TaskDataSource
import net.onefivefour.sessiontimer.core.common.domain.model.Task as DomainTask
import org.junit.jupiter.api.Test

class TaskRepositoryTest {

    private val taskDataSourceMock: TaskDataSource = mockk()

    private val sut = TaskRepository(
        taskDataSourceMock
    )

    @Test
    fun `getAll fetches data from taskDataSource`() = runTest {
        val testTask = DatabaseTask(78L, "Title 1", 3L, 1L)
        coEvery { taskDataSourceMock.getAll(any()) } returns flowOf(listOf(testTask))

        sut.getAll(listOf(1L)).collect()

        coVerify(exactly = 1) { taskDataSourceMock.getAll(any()) }
    }

    @Test
    fun `getAll returns domain model task`() = runTest {
        val testTask = DatabaseTask(
            id = 78L,
            title = "Title 1",
            durationInSeconds = 3L,
            taskGroupId = 1L
        )
        coEvery { taskDataSourceMock.getAll(any()) } returns flowOf(listOf(testTask))

        val tasks = sut.getAll(listOf(1L)).first()

        assertThat(tasks.size).isEqualTo(1)
        val task = tasks.first()
        assertThat(task).isInstanceOf(DomainTask::class.java)
        assertThat(task.id).isEqualTo(78L)
        assertThat(task.title).isEqualTo("Title 1")
        assertThat(task.durationInSeconds).isEqualTo(3.seconds)
        assertThat(task.taskGroupId).isEqualTo(1L)
    }
}
