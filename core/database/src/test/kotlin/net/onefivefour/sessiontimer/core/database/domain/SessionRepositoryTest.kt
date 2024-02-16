package net.onefivefour.sessiontimer.core.database.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.data.FullSession
import net.onefivefour.sessiontimer.core.database.Session as DatabaseSession
import net.onefivefour.sessiontimer.core.database.data.SessionDataSource
import org.junit.jupiter.api.Test

class SessionRepositoryTest {

    private val sessionDataSource: SessionDataSource = mockk()

    private val sut = SessionRepository(
        sessionDataSource
    )

    @Test
    fun `new session insertion should call data source insert method`() = runTest {
        val title = "Sample Session"

        coEvery { sessionDataSource.insert(any()) } returns Unit

        sut.new(title)

        coVerify { sessionDataSource.insert(title) }
    }

    @Test
    fun `getAll should return mapped DomainSessions`() = runTest {
        val databaseSessions = listOf(
            DatabaseSession(1L, "Session 1"),
            DatabaseSession(2L, "Session 2")
        )

        coEvery { sessionDataSource.getAll() } returns flowOf(
            databaseSessions
        )

        sut.getAll().test {
            val result = awaitItem()
            assertThat(result).isEqualTo(databaseSessions.map { it.toDomainSession() })
            awaitComplete()
        }

    }

    @Test
    fun `getFullSession should return mapped DomainSession`() = runTest {
        val sessionId = 1L
        val fullSession = FullSession(
            sessionId,
            "Session 1",
            2L,
            "Task Group 1",
            0xFF00FFL,
            PlayMode.RANDOM.toString(),
            3,
            1L,
            1L,
            "Task 1",
            300
        )

        coEvery { sessionDataSource.getFullSessionById(sessionId) } returns flowOf(
            listOf(fullSession)
        )

        sut.getFullSession(sessionId).test {
            val result = awaitItem()
            assertThat(result).isEqualTo(listOf(fullSession).toDomainSession())
            awaitComplete()
        }

    }

    @Test
    fun `delete session should call data source deleteById method`() = runTest {
        val sessionId = 1L

        coEvery { sessionDataSource.deleteById(any()) } returns Unit

        sut.deleteById(sessionId)

        coVerify { sessionDataSource.deleteById(sessionId) }
    }

    @Test
    fun `setTitle should call data source setTitle method`() = runTest {
        val sessionId = 1L
        val title = "Updated Session Title"

        coEvery { sessionDataSource.setTitle(any(), any()) } returns Unit

        sut.setTitle(sessionId, title)

        coVerify { sessionDataSource.setTitle(sessionId, title) }
    }

    @Test
    fun `getLastInsertId should return value from data source`() = runTest {
        val lastInsertId = 42L

        coEvery { sessionDataSource.getLastInsertId() } returns lastInsertId

        val result = sut.getLastInsertId()

        assertThat(result).isEqualTo(lastInsertId)
    }
}
