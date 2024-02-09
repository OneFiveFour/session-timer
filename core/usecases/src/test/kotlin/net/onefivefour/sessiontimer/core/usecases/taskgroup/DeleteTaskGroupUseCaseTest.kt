package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.taskgroup.DeleteTaskGroupUseCase
import org.junit.jupiter.api.Test

class DeleteTaskGroupUseCaseTest {

    private val taskGroupRepositoryMock: TaskGroupRepository = mockk()
    private val taskRepositoryMock: TaskRepository = mockk()

    private val sut = DeleteTaskGroupUseCase(
        taskGroupRepositoryMock,
        taskRepositoryMock
    )

    @Test
    fun `executing the use case deletes the task group and all of its tasks`() = runTest {
        coEvery { taskGroupRepositoryMock.deleteById(any()) } returns Unit
        coEvery { taskRepositoryMock.deleteByTaskGroup(any()) } returns Unit

        val taskGroupId = 1L

        sut.execute(taskGroupId)

        coVerify(exactly = 1) {
            taskGroupRepositoryMock.deleteById(taskGroupId)
            taskRepositoryMock.deleteByTaskGroup(taskGroupId)
        }
    }

}