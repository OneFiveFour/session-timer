package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.Database
import net.onefivefour.sessiontimer.core.database.TaskGroupQueries
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class TaskGroupDataSourceImplTest {

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private val taskGroupQueries: TaskGroupQueries = mockk()

    private fun sut() = TaskGroupDataSourceImpl(
        taskGroupQueries,
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
    fun `GIVEN taskGroup data WHEN insert is called THEN the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery {
                taskGroupQueries.new(
                    id = any(),
                    title = any(),
                    color = any(),
                    playMode = any(),
                    numberOfRandomTasks = any(),
                    sessionId = any()
                )
            } returns mockk()
            val sessionId = 321L
            val title = "Test TaskGroup Title"
            val color = 123L
            val playMode = PlayMode.RANDOM_SINGLE_TASK.toString()
            val numberOfRandomTasks = 53L

            // WHEN
            sut().insert(title, color, playMode, numberOfRandomTasks, sessionId)

            // THEN
            coVerify(exactly = 1) {
                taskGroupQueries.new(null, title, color, playMode, numberOfRandomTasks, sessionId)
            }
        }

    @Test
    fun `GIVEN a sessionId WHEN getById is called THEN the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery { taskGroupQueries.getById(any()).executeAsOneOrNull() } returns null
            val sessionId = 123L

            // WHEN
            sut().getById(sessionId)

            // THEN
            coVerify(exactly = 1) { taskGroupQueries.getById(sessionId) }
        }

    @Test
    fun `GIVEN a sessionId WHEN getBySessionId is called THEN the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery { taskGroupQueries.getBySessionId(any()).executeAsOneOrNull() } returns null
            val sessionId = 123L

            // WHEN
            sut().getBySessionId(sessionId)

            // THEN
            coVerify(exactly = 1) { taskGroupQueries.getBySessionId(sessionId) }
        }

    @Test
    fun `GIVEN taskGroup data WHEN update is called THEN the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery { taskGroupQueries.update(any(), any(), any(), any(), any()) } returns mockk()
            val taskGroupId = 5L
            val title = "Test TaskGroup Title"
            val color = 123L
            val playMode = PlayMode.RANDOM_SINGLE_TASK.toString()
            val numberOfRandomTasks = 53L

            // WHEN
            sut().update(taskGroupId, title, color, playMode, numberOfRandomTasks)

            // THEN
            coVerify(exactly = 1) {
                taskGroupQueries.update(title, color, playMode, numberOfRandomTasks, taskGroupId)
            }
        }

    @Test
    fun `GIVEN a taskGroupId WHEN deleteById is called THEN the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery { taskGroupQueries.deleteById(any()) } returns mockk()
            val taskGroupId = 123L

            // WHEN
            sut().deleteById(taskGroupId)

            // THEN
            coVerify(exactly = 1) { taskGroupQueries.deleteById(taskGroupId) }
        }

    @Test
    fun `GIVEN a sessionId WHEN deleteBySessionId is called the call is delegated to taskGroupQueries`() =
        runTest {
            // GIVEN
            coEvery { taskGroupQueries.deleteBySessionId(any()) } returns mockk()

            // WHEN
            val sessionId = 123L
            sut().deleteBySessionId(sessionId)

            // THEN
            coVerify(exactly = 1) { taskGroupQueries.deleteBySessionId(sessionId) }
        }
}
