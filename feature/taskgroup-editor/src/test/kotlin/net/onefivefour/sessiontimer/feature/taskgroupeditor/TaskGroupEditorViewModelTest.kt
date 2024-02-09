package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.usecases.taskgroup.GetTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.taskgroup.SetTaskGroupColorUseCase
import net.onefivefour.sessiontimer.core.usecases.taskgroup.SetTaskGroupTitleUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskGroupEditorViewModelTest {

    private val savedStateHandle = SavedStateHandle()

    private val getTaskGroupUseCase : GetTaskGroupUseCase = mockk()
    private val setTaskGroupColorUseCase : SetTaskGroupColorUseCase = mockk()
    private val setTaskGroupTitleUseCase : SetTaskGroupTitleUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private fun sut() = TaskGroupEditorViewModel(
        savedStateHandle,
        getTaskGroupUseCase,
        setTaskGroupTitleUseCase,
        setTaskGroupColorUseCase
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
        savedStateHandle["taskGroupId"] = 1L

        val sut = sut()

        assertThat(sut.uiState.value).isEqualTo(UiState.Initial)
    }

    @Test
    fun `GetTaskGroupUseCase is called on init`() = runTest {
        val taskGroupId = 1L
        savedStateHandle["taskGroupId"] = taskGroupId
        coEvery { getTaskGroupUseCase.execute(taskGroupId) } returns flowOf(
            TaskGroup(
                taskGroupId,
                "TaskGroup 1",
                0xFF0000,
                emptyList(),
                2L
            )
        )

        val sut = sut()

        advanceUntilIdle()

        coVerify(exactly = 1) { getTaskGroupUseCase.execute(taskGroupId) }

        sut.uiState.test {
            val uiState = awaitItem()
            check(uiState is UiState.Success)
            val taskGroup = uiState.taskGroup
            assertThat(taskGroup.id).isEqualTo(taskGroupId)
            assertThat(taskGroup.title).isEqualTo("TaskGroup 1")
            assertThat(taskGroup.color).isEqualTo(0xFF0000)
            assertThat(taskGroup.tasks).isEmpty()
            assertThat(taskGroup.sessionId).isEqualTo(2L)
        }
    }

    @Test
    fun `setTaskGroupTitle delegates to SetTaskGroupTitleUseCase`() = runTest {
        val taskGroupId = 1L
        savedStateHandle["taskGroupId"] = taskGroupId
        coEvery { getTaskGroupUseCase.execute(taskGroupId) } returns flowOf(
            TaskGroup(
                taskGroupId,
                "TaskGroup 1",
                0xFF0000,
                emptyList(),
                2L
            )
        )
        val title = "Test TaskGroup Title"
        coEvery { setTaskGroupTitleUseCase.execute(any(), any()) } returns Unit

        val sut = sut()
        sut.setTaskGroupTitle(taskGroupId, title)

        advanceUntilIdle()

        coVerify {
            setTaskGroupTitleUseCase.execute(taskGroupId, title)
        }
    }

    @Test
    fun `setTaskGroupColor delegates to SetTaskGroupColorUseCase`() = runTest {
        val taskGroupId = 1L
        savedStateHandle["taskGroupId"] = taskGroupId
        coEvery { getTaskGroupUseCase.execute(taskGroupId) } returns flowOf(
            TaskGroup(
                taskGroupId,
                "TaskGroup 1",
                0xFF0000,
                emptyList(),
                2L
            )
        )
        val color = 0xABCDEF
        coEvery { setTaskGroupColorUseCase.execute(any(), any()) } returns Unit

        val sut = sut()
        sut.setTaskGroupColor(taskGroupId, color)

        advanceUntilIdle()

        coVerify {
            setTaskGroupColorUseCase.execute(taskGroupId, color)
        }
    }

}