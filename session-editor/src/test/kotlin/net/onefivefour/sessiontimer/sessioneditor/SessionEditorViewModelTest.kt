package net.onefivefour.sessiontimer.sessioneditor

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.onefivefour.sessiontimer.database.domain.model.Session
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SessionEditorViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val savedStateHandleFake = SavedStateHandle()

    private val getFullSessionUseCaseMock: GetFullSessionUseCase = mockk()

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
    fun `initial session is null`() =  runTest {
        coEvery { getFullSessionUseCaseMock.execute(any()) } returns Session(1L, "Session 1", emptyList())
        savedStateHandleFake["sessionId"] = 1L

        val sut = SessionEditorViewModel(
            savedStateHandleFake,
            getFullSessionUseCaseMock
        )

        assertThat(sut.uiState.value.session).isNull()

    }

    @Test
    fun `useCase is executed on init`() =  runTest {
        val sessionId = 1L
        coEvery { getFullSessionUseCaseMock.execute(any()) } returns Session(sessionId, "Session 1", emptyList())
        savedStateHandleFake["sessionId"] = sessionId

        val sut = SessionEditorViewModel(
            savedStateHandleFake,
            getFullSessionUseCaseMock
        )

        advanceUntilIdle()

        coVerify(exactly = 1) { getFullSessionUseCaseMock.execute(sessionId) }

        val session = sut.uiState.value.session
        checkNotNull(session)
        assertThat(session.id).isEqualTo(sessionId)
        assertThat(session.title).isEqualTo("Session 1")
        assertThat(session.taskGroups).isEmpty()

    }
}