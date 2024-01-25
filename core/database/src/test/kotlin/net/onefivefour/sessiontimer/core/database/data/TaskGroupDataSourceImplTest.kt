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
import net.onefivefour.sessiontimer.core.database.Database
import net.onefivefour.sessiontimer.core.database.TaskGroupQueries
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TaskGroupDataSourceImplTest {

    private val taskGroupQueries: TaskGroupQueries = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private val sut = TaskGroupDataSourceImpl(
        taskGroupQueries,
        testDispatcher
    )

    @BeforeEach
    fun setup() {
        useJvmDatabaseDriver()
        setTestDispatcher()
    }

    @AfterEach
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
    fun `getAll delegates to correct taskGroupQueries call`() = runTest {
        coEvery { taskGroupQueries.getAll(any()).executeAsOneOrNull() } returns null

        val sessionId = 123L
        sut.getAll(sessionId)

        coVerify { taskGroupQueries.getAll(sessionId) }
    }

    @Test
    fun `delete delegates to correct taskGroupQueries call`() = runTest {
        coEvery { taskGroupQueries.delete(any()) } returns mockk()

        val taskGroupId = 123L
        sut.delete(taskGroupId)

        coVerify { taskGroupQueries.delete(taskGroupId) }
    }

    @Test
    fun `insert delegates to correct taskGroupQueries call`() = runTest {
        coEvery { taskGroupQueries.insert(any(), any(), any(), any()) } returns mockk()

        val taskGroupId = 123L
        val sessionId = 321L
        sut.insert(taskGroupId, sessionId)

        coVerify { taskGroupQueries.insert(taskGroupId, null, null, sessionId) }
    }
}
