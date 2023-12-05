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
import net.onefivefour.sessiontimer.database.SessionQueries
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SessionDataSourceImplTest {

    private val sessionQueries: SessionQueries = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private val sut = SessionDataSourceImpl(
        sessionQueries,
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
    fun `getBySessionId delegates to correct sessionQueries call`() = runTest {
        coEvery { sessionQueries.getById(any()).executeAsOneOrNull() } returns null

        val sessionId = 123L
        sut.getById(sessionId)

        coVerify { sessionQueries.getById(sessionId) }
    }

    @Test
    fun `getAll delegates to correct sessionQueries call`() = runTest {
        coEvery { sessionQueries.getAll() } returns mockk()

        sut.getAll()

        coVerify { sessionQueries.getAll() }
    }

    @Test
    fun `delete delegates to correct sessionQueries call`() = runTest {
        coEvery { sessionQueries.delete(any()) } returns mockk()

        val sessionId = 123L
        sut.deleteById(sessionId)

        coVerify { sessionQueries.delete(sessionId) }
    }

    @Test
    fun `insert delegates to correct sessionQueries call`() = runTest {
        coEvery { sessionQueries.insert(any(), any()) } returns mockk()

        val sessionId = 123L
        val title = "title"
        sut.insert(sessionId, title)

        coVerify { sessionQueries.insert(sessionId, title) }
    }
}
