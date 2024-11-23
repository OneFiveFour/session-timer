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

    private val taskQueries: TaskQueries = mockk()

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private val sut = TaskDataSourceImpl(
        taskQueries,
        standardTestDispatcherRule.testDispatcher
    )

    @Before
    fun setup() {
        useJvmDatabaseDriver()
    }

    private fun useJvmDatabaseDriver() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
    }

    @Test
    fun `insert delegates to correct taskQueries call`() = runTest {
        coEvery { taskQueries.new(any(), any(), any(), any()) } returns mockk()

        val taskGroupId = 321L
        val duration = 123L
        val taskTitle = "Test Task Title"
        sut.insert(taskTitle, duration, taskGroupId)

        coVerify {
            taskQueries.new(
                id = null,
                title = taskTitle,
                durationInSeconds = duration,
                taskGroupId = taskGroupId
            )
        }
    }

    @Test
    fun `getByTaskGroupIds delegates to correct taskQueries call`() = runTest {
        coEvery { taskQueries.getByTaskGroupIds(any()).executeAsOneOrNull() } returns null

        val taskGroupIds = listOf(1L, 2L, 3L)
        sut.getByTaskGroupIds(taskGroupIds)

        coVerify { taskQueries.getByTaskGroupIds(taskGroupIds) }
    }

    @Test
    fun `update delegates to correct taskQueries call`() = runTest {
        coEvery { taskQueries.update(any(), any(), any()) } returns mockk()

        val taskId = 123L
        val title = "Test Title"
        val duration = 3L
        sut.update(taskId, title, duration)

        coVerify { taskQueries.update(title, duration, taskId) }
    }

    @Test
    fun `deleteById delegates to correct taskQueries call`() = runTest {
        coEvery { taskQueries.deleteById(any()) } returns mockk()

        val taskId = 123L
        sut.deleteById(taskId)

        coVerify { taskQueries.deleteById(taskId) }
    }

    @Test
    fun `deleteByIds delegates to correct taskQueries call`() = runTest {
        coEvery { taskQueries.deleteByIds(any()) } returns mockk()

        val taskIds = listOf(123L)
        sut.deleteByIds(taskIds)

        coVerify { taskQueries.deleteByIds(taskIds) }
    }

    @Test
    fun `deleteByTaskGroup delegates to correct taskQueries call`() = runTest {
        coEvery { taskQueries.deleteByTaskGroupId(any()) } returns mockk()

        val taskId = 123L
        sut.deleteByTaskGroupId(taskId)

        coVerify { taskQueries.deleteByTaskGroupId(taskId) }
    }
}
