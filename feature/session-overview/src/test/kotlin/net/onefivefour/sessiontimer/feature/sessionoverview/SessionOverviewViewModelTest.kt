package net.onefivefour.sessiontimer.feature.sessionoverview

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.test.StandardTestDispatcherRule
import net.onefivefour.sessiontimer.core.usecases.api.session.DeleteSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.GetAllSessionsUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.NewSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.api.session.SetSessionTitleUseCase
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SessionOverviewViewModelTest {

    @get:Rule
    val standardTestDispatcherRule = StandardTestDispatcherRule()

    private val getAllSessionsUseCase: GetAllSessionsUseCase = mockk()

    private val newSessionUseCase: NewSessionUseCase = mockk()

    private val deleteSessionUseCase: DeleteSessionUseCase = mockk()

    private val setSessionTitleUseCase: SetSessionTitleUseCase = mockk()

    private fun sut() = SessionOverviewViewModel(
        getAllSessionsUseCase,
        newSessionUseCase,
        deleteSessionUseCase,
        setSessionTitleUseCase
    )

    @Test
    fun `WHEN ViewModel is created THEN uiState has correct initial value`() {
        // WHEN
        val sut = sut()

        // THEN
        assertThat(sut.uiState.value).isEqualTo(UiState.Initial)
    }

    @Test
    fun `WHEN ViewModel is created THEN GetAllSessionsUseCase is executed`() = runTest {
        // GIVEN
        coEvery { getAllSessionsUseCase.execute() } returns flowOf(emptyList())

        // WHEN
        sut()
        advanceUntilIdle()

        // THEN
        coVerify(exactly = 1) { getAllSessionsUseCase.execute() }
    }

    @Test
    fun `GIVEN two sessions WHEN ViewModel is created THEN session data is updated in uiState after init`() =
        runTest {
            // GIVEN
            val sessions = listOf(
                Session(id = 1, title = "Session 1", emptyList()),
                Session(id = 2, title = "Session 2", emptyList())
            )
            coEvery { getAllSessionsUseCase.execute() } returns flowOf(sessions)

            // WHEN
            val sut = sut()
            advanceUntilIdle()

            // THEN
            val expectedUiState = UiState.Success(sessions = sessions.toUiSessions())
            assertThat(sut.uiState.value).isEqualTo(expectedUiState)
        }

    @Test
    fun `GIVEN a ViewModel WHEN newSession is called THEN NewSessionUseCase is executed`() =
        runTest {
            // GIVEN
            coEvery { getAllSessionsUseCase.execute() } returns flowOf(emptyList())
            coEvery { newSessionUseCase.execute() } returns Unit
            val sut = sut()

            // WHEN
            sut.newSession()
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) {
                newSessionUseCase.execute()
            }
        }

    @Test
    fun `GIVEN a sessionId WHEN deleteSession is calles THEN DeleteSessionUseCase is executed with that sessionId`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            coEvery { getAllSessionsUseCase.execute() } returns flowOf(emptyList())
            coEvery { deleteSessionUseCase.execute(any()) } returns Unit

            // WHEN
            val sut = sut()
            sut.deleteSession(sessionId)
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) {
                deleteSessionUseCase.execute(sessionId)
            }
        }

    @Test
    fun `GIVEN session data WHEN setSessionTitle is called THEN SetSessionTitleUseCase is executed with that data`() =
        runTest {
            // GIVEN
            coEvery { getAllSessionsUseCase.execute() } returns flowOf(emptyList())
            coEvery { setSessionTitleUseCase.execute(any(), any()) } returns Unit
            val sessionId = 1L
            val title = "Test Session Title"

            // WHEN
            val sut = sut()
            sut.setSessionTitle(sessionId, title)
            advanceUntilIdle()

            // THEN
            coVerify(exactly = 1) {
                setSessionTitleUseCase.execute(sessionId, title)
            }
        }
}
