package net.onefivefour.sessiontimer.core.database.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.TaskGroup as DatabaseTaskGroup
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSource
import org.junit.jupiter.api.Test

class TaskGroupRepositoryTest {

    private val taskGroupDataSource: TaskGroupDataSource = mockk()

    private val sut = TaskGroupRepository(
        taskGroupDataSource
    )

    @Test
    fun `new task group insertion should call data source insert method`() = runTest {
        val title = "Sample Task Group"
        val color = 0xFF0000L
        val playMode = PlayMode.SEQUENCE
        val numberOfRandomTasks = 3
        val sessionId = 1L

        coEvery { taskGroupDataSource.insert(any(), any(), any(), any(), any()) } returns Unit

        sut.new(title, color, playMode, numberOfRandomTasks, sessionId)

        coEvery {
            taskGroupDataSource.insert(
                title = title,
                color = color,
                playMode = playMode.toString(),
                numberOfRandomTasks = numberOfRandomTasks.toLong(),
                sessionId = sessionId
            )
        }
    }

    @Test
    fun `getById should return mapped DomainTaskGroup`() = runTest {
        val taskGroupId = 1L
        val databaseTaskGroup = DatabaseTaskGroup(
            1L,
            "Task Group 1",
            0xFF00FFL,
            PlayMode.RANDOM.toString(),
            2L,
            3
        )

        coEvery { taskGroupDataSource.getById(taskGroupId) } returns flowOf(
            databaseTaskGroup
        )

        sut.getById(taskGroupId).test {
            val result = awaitItem()
            assertThat(result).isEqualTo(databaseTaskGroup.toDomainTaskGroup())
            awaitComplete()
        }
    }

    @Test
    fun `getBySessionId should return mapped DomainTaskGroups`() = runTest {
        val sessionId = 1L
        val databaseTaskGroups = listOf(
            DatabaseTaskGroup(1L, "Task Group 1", 0xFF00FFL, PlayMode.RANDOM.toString(), 2L, 3),
            DatabaseTaskGroup(2L, "Task Group 2", 0x00FFFFL, PlayMode.SEQUENCE.toString(), 1L, 5)
        )

        coEvery { taskGroupDataSource.getBySessionId(sessionId) } returns flowOf(
            databaseTaskGroups
        )

        sut.getBySessionId(sessionId).test {
            val result = awaitItem()
            assertThat(result).isEqualTo(databaseTaskGroups.map { it.toDomainTaskGroup() })
            awaitComplete()
        }
    }

    @Test
    fun `update task group should call data source update method`() = runTest {
        val taskGroupId = 1L
        val title = "Updated Task Group"
        val color = 0x00FF00
        val playMode = PlayMode.SEQUENCE
        val numberOfRandomTasks = 4

        coEvery { taskGroupDataSource.update(any(), any(), any(), any(), any()) } returns Unit

        sut.update(taskGroupId, title, color, playMode, numberOfRandomTasks)

        coVerify {
            taskGroupDataSource.update(
                taskGroupId,
                title,
                color.toLong(),
                playMode.toString(),
                numberOfRandomTasks.toLong()
            )
        }
    }

    @Test
    fun `delete task group should call data source deleteById method`() = runTest {
        val taskGroupId = 1L

        coEvery { taskGroupDataSource.deleteById(any()) } returns Unit

        sut.deleteById(taskGroupId)

        coVerify { taskGroupDataSource.deleteById(taskGroupId) }
    }

    @Test
    fun `getLastInsertId should return value from data source`() = runTest {
        val lastInsertId = 42L

        coEvery { taskGroupDataSource.getLastInsertId() } returns lastInsertId

        val result = sut.getLastInsertId()

        assertThat(result).isEqualTo(lastInsertId)
    }
}
