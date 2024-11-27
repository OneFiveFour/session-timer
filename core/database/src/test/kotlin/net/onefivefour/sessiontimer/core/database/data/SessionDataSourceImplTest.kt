package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.Database
import net.onefivefour.sessiontimer.core.database.SessionQueries
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SessionDataSourceImplTest {

    private val sessionQueries: SessionQueries = mockk()

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private val sut = SessionDataSourceImpl(
        sessionQueries,
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
    fun `insert delegates to correct sessionQueries call`() = runTest {
        coEvery { sessionQueries.new(any(), any()) } returns mockk()

        val title = "title"
        sut.insert(title)

        coVerify { sessionQueries.new(null, title) }
    }

    @Test
    fun `getAll delegates to correct sessionQueries call`() = runTest {
        coEvery { sessionQueries.getAll() } returns mockk()

        sut.getAll()

        coVerify { sessionQueries.getAll() }
    }

    @Test
    fun `getFullSessionById delegates to correct sessionQueries call`() = runTest {
        coEvery { sessionQueries.denormalizedSessionView(any()).executeAsOneOrNull() } returns null

        val sessionId = 123L
        sut.getDenormalizedSessionView(sessionId)

        coVerify { sessionQueries.denormalizedSessionView(sessionId) }
    }

    @Test
    fun `deleteById delegates to correct sessionQueries call`() = runTest {
        coEvery { sessionQueries.deleteById(any()) } returns mockk()

        val sessionId = 123L
        sut.deleteById(sessionId)

        coVerify { sessionQueries.deleteById(sessionId) }
    }

    @Test
    fun `setTitle delegates to correct sessionQueries call`() = runTest {
        coEvery { sessionQueries.setTitle(any(), any()) } returns mockk()

        val sessionId = 123L
        val title = "Test Title"
        sut.setTitle(sessionId, title)

        coVerify { sessionQueries.setTitle(title, sessionId) }
    }
}
