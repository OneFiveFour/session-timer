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
import net.onefivefour.sessiontimer.BlopQueries
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.awt.Color

@OptIn(ExperimentalCoroutinesApi::class)
class BlipDataSourceImplTest {

    private val blopQueries: BlopQueries = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private val sut = BlopDataSourceImpl(
        blopQueries,
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
    fun `getAll delegates to correct blopQueries call`() = runTest {
        coEvery { blopQueries.getAll(any()).executeAsOneOrNull() } returns null

        val sessionId = 123L
        sut.getAll(sessionId)

        coVerify { blopQueries.getAll(sessionId) }
    }

    @Test
    fun `delete delegates to correct blopQueries call`() = runTest {
        coEvery { blopQueries.delete(any()) } returns mockk()

        val blopId = 123L
        sut.delete(blopId)

        coVerify { blopQueries.delete(blopId) }
    }

    @Test
    fun `insert delegates to correct blopQueries call`() = runTest {
        coEvery { blopQueries.insert(any(), any(), any(), any()) } returns mockk()

        val blipId = 123L
        val title = "title"
        val color = 0xFF0000.toLong()
        val blopId = 321L
        sut.insert(blopId, title, color, blopId)

        coVerify { blopQueries.insert(blopId, title, color, blopId) }
    }
}
