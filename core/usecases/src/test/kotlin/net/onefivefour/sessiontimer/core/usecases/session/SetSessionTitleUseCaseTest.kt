package net.onefivefour.sessiontimer.core.usecases.session

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import org.junit.Test


class SetSessionTitleUseCaseTest {

    private val sessionRepository: SessionRepository = mockk(relaxed = true)

    private val sut = SetSessionTitleUseCase(
        sessionRepository
    )

    @Test
    fun `executing the use case sets the title to the session`() = runTest {
        val sessionId = 1L
        val title = "Test Session Title"

        sut.execute(sessionId, title)

        coVerify(exactly = 1) {
            sessionRepository.setTitle(sessionId, title)
        }
    }
}
