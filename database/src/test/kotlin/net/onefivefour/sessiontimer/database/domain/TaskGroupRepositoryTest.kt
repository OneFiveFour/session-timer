package net.onefivefour.sessiontimer.database.domain

import android.graphics.Color
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.database.TaskGroup as DatabaseTaskGroup
import net.onefivefour.sessiontimer.database.data.TaskGroupDataSource
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
        val testTaskGroup = DatabaseTaskGroup(78L, "Title 1", 123L, 1L)
        coEvery { taskGroupDataSource.getAll(any()) } returns flowOf(listOf(testTaskGroup))

        val sessionId = 1L
        val taskGroups = sut.getAll(sessionId).first()

        assertThat(taskGroups.size).isEqualTo(1)
        val taskGroup = taskGroups.first()
        assertThat(taskGroup).isInstanceOf(DatabaseTaskGroup::class.java)
        assertThat(taskGroup.id).isEqualTo(78L)
        assertThat(taskGroup.title).isEqualTo("Title 1")
        assertThat(taskGroup.color).isEqualTo(123L)
        assertThat(taskGroup.sessionId).isEqualTo(1L)
    }

}