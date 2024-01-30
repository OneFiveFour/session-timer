package net.onefivefour.sessiontimer.core.usecases

import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDefaultValuesProvider
import org.junit.jupiter.api.Test

class NewSessionUseCaseTest {

    private val sessionRepository: SessionRepository = mockk()

    private val defaultValuesProvider : DatabaseDefaultValuesProvider = mockk()

    private val sut = NewSessionUseCase(
        sessionRepository,
        defaultValuesProvider
    )

    @Test
    fun `executing the use case creates a new session with the default title`() = runTest {
        coEvery { defaultValuesProvider.getSessionTitle() } returns "Default Title"
        coEvery { sessionRepository.new(any()) } returns Unit

        sut.execute()

        coVerify(ordering = Ordering.ORDERED) {
            defaultValuesProvider.getSessionTitle()
            sessionRepository.new("Default Title")
        }
    }
}