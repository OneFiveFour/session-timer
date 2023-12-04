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
import net.onefivefour.sessiontimer.BlipQueries
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class BlipDataSourceImplTest {

    private val blipQueries: BlipQueries = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private val sut = BlipDataSourceImpl(
        blipQueries,
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
    fun `getAll delegates to correct blipQueries call`() = runTest {
        coEvery { blipQueries.getAll(any()).executeAsOneOrNull() } returns null

        val sessionId = 123L
        sut.getAll(sessionId)

        coVerify { blipQueries.getAll(sessionId) }
    }

    @Test
    fun `delete delegates to correct blipQueries call`() = runTest {
        coEvery { blipQueries.delete(any()) } returns mockk()

        val blipId = 123L
        sut.delete(blipId)

        coVerify { blipQueries.delete(blipId) }
    }

    @Test
    fun `insert delegates to correct blipQueries call`() = runTest {
        coEvery { blipQueries.insert(any(), any(), any()) } returns mockk()

        val blipId = 123L
        val title = "title"
        val color = 0xFF0000.toLong()
        sut.insert(blipId, title, color)

        coVerify { blipQueries.insert(blipId, title, color) }
    }
}
