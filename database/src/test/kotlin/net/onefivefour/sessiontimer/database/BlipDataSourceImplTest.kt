package net.onefivefour.sessiontimer.database

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
import net.onefivefour.sessiontimer.database.data.TaskDataSourceImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class TaskDataSourceImplTest {

    private val taskQueries: TaskQueries = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private val sut = TaskDataSourceImpl(
        taskQueries,
        testDispatcher
    )

    @BeforeEach
    fun setup() {
        useJvmDatabaseDriver()
    }

    @AfterEach
    fun teardown() {
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
    fun `getAll delegates to correct taskQueries call`() = runTest {
        coEvery { taskQueries.getAll(any()).executeAsOneOrNull() } returns null

        val sessionId = 123L
        sut.getAll(sessionId)

        coVerify { taskQueries.getAll(sessionId) }
    }

    @Test
    fun `delete delegates to correct taskQueries call`() = runTest {
        coEvery { taskQueries.delete(any()) } returns mockk()

        val taskId = 123L
        sut.delete(taskId)

        coVerify { taskQueries.delete(taskId) }
    }

    @Test
    fun `insert delegates to correct taskQueries call`() = runTest {
        coEvery { taskQueries.insert(any(), any(), any()) } returns mockk()

        val taskId = 123L
        val title = "title"
        val color = 0xFF0000.toLong()
        sut.insert(taskId, title, color)

        coVerify { taskQueries.insert(taskId, title, color) }
    }
}
