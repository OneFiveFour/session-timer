package net.onefivefour.sessiontimer.sessionoverview

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.onefivefour.sessiontimer.database.domain.SessionRepository
import net.onefivefour.sessiontimer.database.domain.model.Session
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SessionOverviewViewModelTest {

    private val sessionRepository: SessionRepository = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private fun sut() = SessionOverviewViewModel(sessionRepository)

    @BeforeEach
    fun setup() {
        setTestDispatcher()
    }

    @AfterEach
    fun teardown() {
        unsetTestDispatcher()
    }

    private fun setTestDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    private fun unsetTestDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState has correct initial value`() {
        val sut = sut()

        assertThat(sut.uiState.value).isEqualTo(UiState())
    }

    @Test
    fun `sessionRepository is called on init`() = runTest {
        coEvery { sessionRepository.getAll() } returns flowOf(emptyList())

        sut()
        advanceTimeBy(1)

        coVerify(exactly = 1) { sessionRepository.getAll() }
    }

    @Test
    fun `session data is updated in uiState after init`() = runTest {
        val sessions = listOf(
            Session(id = 1, title = "Session 1", emptyList()),
            Session(id = 2, title = "Session 2", emptyList())
        )
        coEvery { sessionRepository.getAll() } returns flowOf(sessions)

        val sut = sut()
        advanceTimeBy(1)

        val expectedUiState = UiState(sessions = sessions)
        assertThat(sut.uiState.value).isEqualTo(expectedUiState)
    }
}
