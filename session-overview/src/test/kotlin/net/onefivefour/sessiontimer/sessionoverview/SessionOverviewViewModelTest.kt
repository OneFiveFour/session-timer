package net.onefivefour.sessiontimer.sessionoverview

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.database.domain.SessionRepository
import net.onefivefour.sessiontimer.database.domain.model.Session
import org.junit.Test

class SessionOverviewViewModelTest {

    private val sessionRepository: SessionRepository = mockk()

    private fun sut() = SessionOverviewViewModel(sessionRepository)

    @Test
    fun `session data is updated in UI state`() = runTest {
        // Given
        val sessions = listOf(
            Session(id = 1, title = "Session 1", emptyList()),
            Session(id = 2, title = "Session 2", emptyList())
        )
        coEvery { sessionRepository.getAll() } returns flowOf(sessions)

        // When
        // Trigger the init block to start collecting sessions
        // This will update the UI state with the collected sessions
        val sut = sut()

        // Then
        // Verify that the UI state is updated with the collected sessions
        val expectedUiState = UiState(sessions = sessions)
        assert(sut.uiState.value == expectedUiState)
    }
}
