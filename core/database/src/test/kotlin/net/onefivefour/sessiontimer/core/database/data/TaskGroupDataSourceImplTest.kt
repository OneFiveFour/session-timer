package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.Database
import net.onefivefour.sessiontimer.core.database.TaskGroupQueries
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TaskGroupDataSourceImplTest {

    private val taskGroupQueries: TaskGroupQueries = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private val sut = TaskGroupDataSourceImpl(
        taskGroupQueries,
        testDispatcher
    )

    @Before
    fun setup() {
        useJvmDatabaseDriver()
        setTestDispatcher()
    }

    @After
    fun teardown() {
        unsetTestDispatcher()
    }

    private fun useJvmDatabaseDriver() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
    }

    private fun setTestDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    private fun unsetTestDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `insert delegates to correct taskGroupQueries call`() = runTest {
        coEvery {
            taskGroupQueries.new(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns mockk()

        val sessionId = 321L
        val title = "Test TaskGroup Title"
        val color = 123L
        val playMode = PlayMode.RANDOM.toString()
        val numberOfRandomTasks = 53L
        sut.insert(
            title,
            color,
            playMode,
            numberOfRandomTasks,
            sessionId
        )

        coVerify {
            taskGroupQueries.new(
                null,
                title,
                color,
                playMode,
                numberOfRandomTasks,
                sessionId
            )
        }
    }

    @Test
    fun `getById delegates to correct taskGroupQueries call`() = runTest {
        coEvery { taskGroupQueries.getById(any()).executeAsOneOrNull() } returns null

        val sessionId = 123L
        sut.getById(sessionId)

        coVerify { taskGroupQueries.getById(sessionId) }
    }

    @Test
    fun `getBySessionId delegates to correct taskGroupQueries call`() = runTest {
        coEvery { taskGroupQueries.getBySessionId(any()).executeAsOneOrNull() } returns null

        val sessionId = 123L
        sut.getBySessionId(sessionId)

        coVerify { taskGroupQueries.getBySessionId(sessionId) }
    }

    @Test
    fun `update delegates to correct taskGroupQueries call`() = runTest {
        coEvery { taskGroupQueries.update(any(), any(), any(), any(), any()) } returns mockk()

        val taskGroupId = 5L
        val title = "Test TaskGroup Title"
        val color = 123L
        val playMode = PlayMode.RANDOM.toString()
        val numberOfRandomTasks = 53L
        sut.update(
            taskGroupId,
            title,
            color,
            playMode,
            numberOfRandomTasks
        )

        coVerify {
            taskGroupQueries.update(
                title,
                color,
                playMode,
                numberOfRandomTasks,
                taskGroupId
            )
        }
    }

    @Test
    fun `deleteById delegates to correct taskGroupQueries call`() = runTest {
        coEvery { taskGroupQueries.deleteById(any()) } returns mockk()

        val taskGroupId = 123L
        sut.deleteById(taskGroupId)

        coVerify { taskGroupQueries.deleteById(taskGroupId) }
    }

    @Test
    fun `deleteBySessionId delegates to correct taskGroupQueries call`() = runTest {
        coEvery { taskGroupQueries.deleteBySessionId(any()) } returns mockk()

        val sessionId = 123L
        sut.deleteBySessionId(sessionId)

        coVerify { taskGroupQueries.deleteBySessionId(sessionId) }
    }
}
