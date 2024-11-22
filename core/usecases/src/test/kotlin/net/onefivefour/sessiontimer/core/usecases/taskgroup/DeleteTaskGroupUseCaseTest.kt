package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.Test


class DeleteTaskGroupUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk()
    private val taskRepository: TaskRepository = mockk()

    private val sut = DeleteTaskGroupUseCase(
        taskGroupRepository,
        taskRepository
    )

    @Test
    fun `executing the use case deletes the task group and all of its tasks`() = runTest {
        coEvery { taskGroupRepository.deleteById(any()) } returns Unit
        coEvery { taskRepository.deleteByTaskGroupId(any()) } returns Unit

        val taskGroupId = 1L

        sut.execute(taskGroupId)

        coVerify(exactly = 1) {
            taskGroupRepository.deleteById(taskGroupId)
            taskRepository.deleteByTaskGroupId(taskGroupId)
        }
    }
}
