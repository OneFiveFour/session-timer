package net.onefivefour.sessiontimer.core.database.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.DenormalizedSessionView
import net.onefivefour.sessiontimer.core.database.Session as DatabaseSession
import net.onefivefour.sessiontimer.core.database.data.SessionDataSource
import org.junit.Test

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
        val denormalizedSessionView = DenormalizedSessionView(
            sessionId = sessionId,
            sessionTitle = "Session 1",
            taskGroupId = 2L,
            taskGroupTitle = "Task Group 1",
            taskGroupColor = 0xFF00FFL,
            taskGroupPlayMode = PlayMode.RANDOM_SINGLE_TASK.toString(),
            taskGroupNumberOfRandomTasks = 3,
            taskId = 1L,
            taskTaskGroupId = 1L,
            taskTitle = "Task 1",
            taskDuration = 300
        )

        coEvery { sessionDataSource.getDenormalizedSessionView(sessionId) } returns flowOf(
            listOf(denormalizedSessionView)
        )

        sut.getFullSession(sessionId).test {
            val result = awaitItem()
            assertThat(result).isEqualTo(listOf(denormalizedSessionView).toDomainSession())
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

    @Test
    fun `delete last taskGroup `() = runTest {
        val lastInsertId = 42L

        coEvery { sessionDataSource.getLastInsertId() } returns lastInsertId

        val result = sut.getLastInsertId()

        assertThat(result).isEqualTo(lastInsertId)
    }
}
