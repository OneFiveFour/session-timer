package net.onefivefour.sessiontimer.core.database.domain

import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.Session as DatabaseSession
import net.onefivefour.sessiontimer.core.database.data.SessionDataSource
import net.onefivefour.sessiontimer.core.database.domain.model.Session as DomainSession
import org.junit.jupiter.api.Test


class SessionRepositoryTest {

    private val sessionDataSourceMock :SessionDataSource = mockk()

    private val sut = SessionRepository(
        sessionDataSourceMock
    )

    @Test
    fun `getAll fetches data from sessionDataSource`() = runTest {
        val testSession = DatabaseSession(78L, "Title 1")
        every { sessionDataSourceMock.getAll() } returns flowOf(listOf(testSession))

        sut.getAll().first()

        coVerify(exactly = 1) { sessionDataSourceMock.getAll() }
    }

    @Test
    fun `getAll returns domain model session`() = runTest {
        val testSession = DatabaseSession(78L, "Title 1")
        every { sessionDataSourceMock.getAll() } returns flowOf(listOf(testSession))

        val sessions = sut.getAll().first()

        assertThat(sessions.size).isEqualTo(1)
        val session = sessions.first()
        assertThat(session).isInstanceOf(DomainSession::class.java)
        assertThat(session.id).isEqualTo(78L)
        assertThat(session.title).isEqualTo("Title 1")
        assertThat(session.taskGroups).isEmpty()
    }

}