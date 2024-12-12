package net.onefivefour.sessiontimer.core.usecases.session

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import org.junit.Test

internal class GetAllSessionsUseCaseTest {

    private val sessionRepository: SessionRepository = mockk()

    private fun sut() = GetAllSessionsUseCaseImpl(
        sessionRepository
    )

    @Test
    fun `GIVEN a full session WHEN executing the UseCase THEN all sessions without their taskGroups`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            val title = "Sesssion Title"
            coEvery { sessionRepository.getAllSessions() } returns flowOf(
                listOf(
                    Session(sessionId, title, emptyList())
                )
            )

            // WHEN
            val result = sut().execute()

            // THEN
            result.test {
                val sessionList = awaitItem()
                assertThat(sessionList.size).isEqualTo(1)
                val session = sessionList.first()
                assertThat(session.id).isEqualTo(sessionId)
                assertThat(session.title).isEqualTo(title)
                assertThat(session.taskGroups).isEmpty()
                awaitComplete()
            }

            coVerify(exactly = 1) {
                sessionRepository.getAllSessions()
            }
        }
}
