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

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private val sessionQueries: SessionQueries = mockk()

    private fun sut() = SessionDataSourceImpl(
        sessionQueries,
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
    fun `GIVEN data for new session WHEN insert is called THEN the call is delegated to sessionQueries`() =
        runTest {
            // GIVEN
            coEvery { sessionQueries.new(any(), any()) } returns mockk()
            val title = "title"

            // WHEN
            sut().insert(title)

            // THEN
            coVerify(exactly = 1) { sessionQueries.new(null, title) }
        }

    @Test
    fun `GIVEN mocked sessions WHEN getAll is called THEN the call is delegated to sessionQueries`() =
        runTest {
            // GIVEN
            coEvery { sessionQueries.getAll() } returns mockk()

            // WHEN
            sut().getAll()

            // THEN
            coVerify(exactly = 1) { sessionQueries.getAll() }
        }

    @Test
    fun `GIVEN a sessionId WHEN getDenormalizedSessionView is called THEN the call is delegated to sessionQueries`() =
        runTest {
            // GIVEN
            coEvery {
                sessionQueries.denormalizedSessionView(any()).executeAsOneOrNull()
            } returns null
            val sessionId = 123L

            // WHEN
            sut().getDenormalizedSessionView(sessionId)

            // THEN
            coVerify(exactly = 1) { sessionQueries.denormalizedSessionView(sessionId) }
        }

    @Test
    fun `GIVEN a sessionId WHEN deleteById is called THEN the call is delegated to sessionQueries`() =
        runTest {
            // GIVEN
            coEvery { sessionQueries.deleteById(any()) } returns mockk()
            val sessionId = 123L

            // WHEN
            sut().deleteById(sessionId)

            // THEN
            coVerify(exactly = 1) { sessionQueries.deleteById(sessionId) }
        }

    @Test
    fun `GIVEN a sessionId and title WHEN setTitle is called THEN the call is delegated to sessionQueries`() =
        runTest {
            // GIVEN
            coEvery { sessionQueries.setTitle(any(), any()) } returns mockk()
            val sessionId = 123L
            val title = "Test Title"

            // WHEN
            sut().setTitle(sessionId, title)

            // THEN
            coVerify(exactly = 1) { sessionQueries.setTitle(title, sessionId) }
        }
}
