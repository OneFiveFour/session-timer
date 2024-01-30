package net.onefivefour.sessiontimer.core.database.domain

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.TaskGroup as DatabaseTaskGroup
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSource
import net.onefivefour.sessiontimer.core.database.domain.model.TaskGroup as DomainTaskGroup
import org.junit.jupiter.api.Test

class TaskGroupRepositoryTest {

    private val taskGroupDataSource: TaskGroupDataSource = mockk()

    private val sut = TaskGroupRepository(
        taskGroupDataSource
    )

    @Test
    fun `getAll fetches data from taskGroupDataSource`() = runTest {
        val testTaskGroup = DatabaseTaskGroup(78L, "Title 1", 123L, 1L)
        coEvery { taskGroupDataSource.getAll(any()) } returns flowOf(listOf(testTaskGroup))

        val sessionId = 1L
        sut.getAll(sessionId).first()

        coVerify(exactly = 1) { taskGroupDataSource.getAll(sessionId) }
    }

    @Test
    fun `getAll returns domain model taskGroup`() = runTest {
        val testTaskGroup = DatabaseTaskGroup(78L, "Title 1", 0xFF0000, 1L)
        coEvery { taskGroupDataSource.getAll(any()) } returns flowOf(listOf(testTaskGroup))

        val sessionId = 1L
        val taskGroups = sut.getAll(sessionId).first()

        assertThat(taskGroups.size).isEqualTo(1)
        val taskGroup = taskGroups.first()
        assertThat(taskGroup).isInstanceOf(DomainTaskGroup::class.java)
        assertThat(taskGroup.id).isEqualTo(78L)
        assertThat(taskGroup.title).isEqualTo("Title 1")
        assertThat(taskGroup.color).isEqualTo(0xFF0000)
        assertThat(taskGroup.sessionId).isEqualTo(1L)
    }
}
