package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.Database
import net.onefivefour.sessiontimer.core.database.TaskQueries
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class TaskDataSourceImplTest {

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private val taskQueries: TaskQueries = mockk()

    private fun sut() = TaskDataSourceImpl(
        taskQueries,
        standardTestDispatcherRule.testDispatcher
    )

    private fun useJvmDatabaseDriver() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
    }

    @Before
    fun setup() {
        useJvmDatabaseDriver()
    }

    @Test
    fun `GIVEN data for a task WHEN insert is called THEN the call is delegated to taskQueries`() =
        runTest {
            // GIVEN
            coEvery { taskQueries.new(any(), any(), any(), any()) } returns mockk()
            val taskGroupId = 321L
            val duration = 123L
            val taskTitle = "Test Task Title"

            // WHEN
            sut().insert(taskTitle, duration, taskGroupId)

            // THEN
            coVerify(exactly = 1) {
                taskQueries.new(
                    id = null,
                    title = taskTitle,
                    durationInSeconds = duration,
                    taskGroupId = taskGroupId
                )
            }
        }

    @Test
    fun `GIVEN taskGroupIds WHEN getByTaskGroupIds is called THEN the call is delegated to taskQueries`() =
        runTest {
            // GIVEN
            coEvery { taskQueries.getByTaskGroupIds(any()).executeAsOneOrNull() } returns null
            val taskGroupIds = listOf(1L, 2L, 3L)

            // WHEN
            sut().getByTaskGroupIds(taskGroupIds)

            // THEN
            coVerify(exactly = 1) { taskQueries.getByTaskGroupIds(taskGroupIds) }
        }

    @Test
    fun `GIVEN task data WHEN update is called THEN the call is delegated to taskQueries`() =
        runTest {
            // GIVEN
            coEvery { taskQueries.update(any(), any(), any()) } returns mockk()
            val taskId = 123L
            val title = "Test Title"
            val duration = 3L

            // WHEN
            sut().update(taskId, title, duration)

            // THEN
            coVerify(exactly = 1) { taskQueries.update(title, duration, taskId) }
        }

    @Test
    fun `GIVEN a taskId WHEN deleteById is called THEN the call is delegated to taskQueries`() =
        runTest {
            // GIVEN
            coEvery { taskQueries.deleteById(any()) } returns mockk()

            // WHEN
            val taskId = 123L
            sut().deleteById(taskId)

            // THEN
            coVerify(exactly = 1) { taskQueries.deleteById(taskId) }
        }

    @Test
    fun `GIVEN a list of taskIds WHEN deleteByIds is called THEN the call is delegated to taskQueries`() =
        runTest {
            // GIVEN
            coEvery { taskQueries.deleteByIds(any()) } returns mockk()

            // WHEN
            val taskIds = listOf(123L)
            sut().deleteByIds(taskIds)

            // THEN
            coVerify(exactly = 1) { taskQueries.deleteByIds(taskIds) }
        }

    @Test
    fun `GIVEN a taskId WHEN deleteByTaskGroup is called THEN the call is delegated to taskQueries`() =
        runTest {
            // GIVEN
            coEvery { taskQueries.deleteByTaskGroupId(any()) } returns mockk()

            // WHEN
            val taskId = 123L
            sut().deleteByTaskGroupId(taskId)

            // THEN
            coVerify(exactly = 1) { taskQueries.deleteByTaskGroupId(taskId) }
        }
}
