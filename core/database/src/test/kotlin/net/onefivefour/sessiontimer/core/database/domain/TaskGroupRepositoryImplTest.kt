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
import org.junit.Test

class TaskGroupRepositoryImplTest {

    private val taskGroupDataSource: TaskGroupDataSource = mockk()

    private fun sut() = TaskGroupRepositoryImpl(
        taskGroupDataSource
    )

    @Test
    fun `GIVEN task group data WHEN newTaskGroup is called THEN the call is delegated to taskGroupDataSource`() =
        runTest {
            // GIVEN
            val title = "Sample Task Group"
            val color = 0xFF0000L
            val playMode = PlayMode.SEQUENCE
            val numberOfRandomTasks = 3
            val sessionId = 1L
            coEvery { taskGroupDataSource.insert(any(), any(), any(), any(), any()) } returns Unit

            // WHEN
            sut().newTaskGroup(title, color, playMode, numberOfRandomTasks, sessionId)

            // THEN
            coVerify(exactly = 1) {
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
    fun `GIVEN a taskGroupId WHEN getTaskGroupById is called THEN the mapped DomainTaskGroup should be returned`() =
        runTest {
            // GIVEN
            val taskGroupId = 1L
            val databaseTaskGroup = DatabaseTaskGroup(
                1L,
                "Task Group 1",
                0xFF00FFL,
                PlayMode.RANDOM_SINGLE_TASK.toString(),
                2L,
                3
            )
            coEvery { taskGroupDataSource.getById(taskGroupId) } returns flowOf(
                databaseTaskGroup
            )

            // WHEN
            val taskGroup = sut().getTaskGroupById(taskGroupId)

            // THEN
            taskGroup.test {
                val result = awaitItem()
                assertThat(result).isEqualTo(databaseTaskGroup.toDomainTaskGroup())
                awaitComplete()
            }
        }

    @Test
    fun `GIVEN a sessionId WHEN getTaskGroupBySessionId is called THEN the mapped DomainTaskGroup should be returned`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            val databaseTaskGroups = listOf(
                DatabaseTaskGroup(
                    1L,
                    "Task Group 1",
                    0xFF00FFL,
                    PlayMode.RANDOM_SINGLE_TASK.toString(),
                    2L,
                    3
                ),
                DatabaseTaskGroup(
                    2L,
                    "Task Group 2",
                    0x00FFFFL,
                    PlayMode.SEQUENCE.toString(),
                    1L,
                    5
                )
            )
            coEvery { taskGroupDataSource.getBySessionId(sessionId) } returns flowOf(
                databaseTaskGroups
            )

            // WHEN
            val taskGroup = sut().getTaskGroupBySessionId(sessionId)

            // THEN
            taskGroup.test {
                val result = awaitItem()
                assertThat(result).isEqualTo(databaseTaskGroups.map { it.toDomainTaskGroup() })
                awaitComplete()
            }
        }

    @Test
    fun `GIVEN taskGroup data WHEN updateTaskGroup is called THEN the call is delegated to taskGroupDataSource`() =
        runTest {
            // GIVEN
            val taskGroupId = 1L
            val title = "Updated Task Group"
            val color = 0x00FF00
            val playMode = PlayMode.SEQUENCE
            val numberOfRandomTasks = 4
            coEvery { taskGroupDataSource.update(any(), any(), any(), any(), any()) } returns Unit

            // WHEN
            sut().updateTaskGroup(taskGroupId, title, color, playMode, numberOfRandomTasks)

            // THEN
            coVerify(exactly = 1) {
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
    fun `GIVEN a taskGroupId WHEN deleteTaskGroupById is called THEN the call is delegated to taskGroupDataSource`() =
        runTest {
            // GIVEN
            val taskGroupId = 1L
            coEvery { taskGroupDataSource.deleteById(any()) } returns Unit

            // WHEN
            sut().deleteTaskGroupById(taskGroupId)

            // THEN
            coVerify(exactly = 1) { taskGroupDataSource.deleteById(taskGroupId) }
        }

    @Test
    fun `GIVEN a last inserted id WHEN getLastInsertId is called THEN the value from taskGroupDataSource should be returned`() =
        runTest {
            // GIVEN
            val lastInsertId = 42L
            coEvery { taskGroupDataSource.getLastInsertId() } returns lastInsertId

            // WHEN
            val result = sut().getLastInsertId()

            // THEN
            assertThat(result).isEqualTo(lastInsertId)
        }
}
