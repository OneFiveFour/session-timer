package net.onefivefour.sessiontimer.core.usecases.session

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import org.junit.Test

class SetSessionTitleUseCaseTest {

    private val sessionRepository: SessionRepository = mockk(relaxed = true)

    private fun sut() = SetSessionTitleUseCaseImpl(
        sessionRepository
    )

    @Test
    fun `GIVEN session data WHEN executing the UseCase THEN the repository is called to set the session title`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            val title = "Test Session Title"

            // WHEN
            sut().execute(sessionId, title)

            // THEN
            coVerify(exactly = 1) {
                sessionRepository.setSessionTitle(sessionId, title)
            }
        }
}
