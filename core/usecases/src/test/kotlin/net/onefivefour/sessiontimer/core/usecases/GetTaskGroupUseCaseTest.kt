package net.onefivefour.sessiontimer.core.usecases

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import org.junit.jupiter.api.Test


class GetTaskGroupUseCaseTest {

    private val taskGroupRepository : TaskGroupRepository = mockk()

    private val sut = GetTaskGroupUseCase(
        taskGroupRepository
    )

    @Test
    fun `executing the use case returns the correct task group`() = runTest {
        val taskGroupId = 1L
        coEvery { taskGroupRepository.get(taskGroupId) } returns flowOf(
            TaskGroup(taskGroupId, "TaskGroup 1", 0xFF0000, emptyList(), 2L)
        )

        sut.execute(taskGroupId).test {
            val taskGroup = awaitItem()
            assertThat(taskGroup.id).isEqualTo(taskGroupId)
            assertThat(taskGroup.title).isEqualTo("TaskGroup 1")
            assertThat(taskGroup.color).isEqualTo(0xFF0000)
            assertThat(taskGroup.tasks).isEmpty()
            assertThat(taskGroup.sessionId).isEqualTo(2L)
            awaitComplete()
        }

        coVerify(exactly = 1) {
            taskGroupRepository.get(taskGroupId)
        }
    }

}