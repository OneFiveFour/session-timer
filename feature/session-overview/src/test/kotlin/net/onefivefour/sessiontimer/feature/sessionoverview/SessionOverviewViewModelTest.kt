package net.onefivefour.sessiontimer.feature.sessionoverview

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.usecases.session.DeleteSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.session.GetAllSessionsUseCase
import net.onefivefour.sessiontimer.core.usecases.session.NewSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.session.SetSessionTitleUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SessionOverviewViewModelTest {

    private val getAllSessionsUseCase: GetAllSessionsUseCase = mockk()

    private val newSessionUseCase: NewSessionUseCase = mockk()
    private val deleteSessionUseCase: DeleteSessionUseCase = mockk()
    private val setSessionTitleUseCase: SetSessionTitleUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private fun sut() = SessionOverviewViewModel(
        getAllSessionsUseCase,
        newSessionUseCase,
        deleteSessionUseCase,
        setSessionTitleUseCase
    )

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

        assertThat(sut.uiState.value).isEqualTo(UiState.Initial)
    }

    @Test
    fun `GetAllSessionsUseCase is called on init`() = runTest {
        coEvery { getAllSessionsUseCase.execute() } returns flowOf(emptyList())

        sut()
        advanceTimeBy(1)

        coVerify(exactly = 1) { getAllSessionsUseCase.execute() }
    }

    @Test
    fun `session data is updated in uiState after init`() = runTest {
        val sessions = listOf(
            Session(id = 1, title = "Session 1", emptyList()),
            Session(id = 2, title = "Session 2", emptyList())
        )
        coEvery { getAllSessionsUseCase.execute() } returns flowOf(sessions)

        val sut = sut()
        advanceTimeBy(1)

        val expectedUiState = UiState.Success(sessions = sessions)
        assertThat(sut.uiState.value).isEqualTo(expectedUiState)
    }

    @Test
    fun `newSession delegates to NewSessionUseCase`() = runTest {
        coEvery { getAllSessionsUseCase.execute() } returns flowOf(emptyList())
        coEvery { newSessionUseCase.execute() } returns Unit

        val sut = sut()
        sut.newSession()

        advanceUntilIdle()

        coVerify(exactly = 1) {
            newSessionUseCase.execute()
        }
    }

    @Test
    fun `deleteSession delegates to DeleteSessionUseCase`() = runTest {
        coEvery { getAllSessionsUseCase.execute() } returns flowOf(emptyList())
        coEvery { deleteSessionUseCase.execute(any()) } returns Unit
        val sessionId = 1L

        val sut = sut()
        sut.deleteSession(sessionId)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            deleteSessionUseCase.execute(sessionId)
        }
    }

    @Test
    fun `setSessionTitle delegates to SetSessionTitleUseCase`() = runTest {
        coEvery { getAllSessionsUseCase.execute() } returns flowOf(emptyList())
        coEvery { setSessionTitleUseCase.execute(any(), any()) } returns Unit
        val sessionId = 1L
        val title = "Test Session Title"

        val sut = sut()
        sut.setSessionTitle(sessionId, title)

        advanceUntilIdle()

        coVerify(exactly = 1) {
            setSessionTitleUseCase.execute(sessionId, title)
        }
    }
}
