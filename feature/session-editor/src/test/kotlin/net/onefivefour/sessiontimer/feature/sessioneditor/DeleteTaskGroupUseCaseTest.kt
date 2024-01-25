package net.onefivefour.sessiontimer.feature.sessioneditor

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
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
        coEvery { taskGroupRepositoryMock.delete(any()) } returns Unit
        coEvery { taskRepositoryMock.deleteByTaskGroup(any()) } returns Unit

        val taskGroupId = 1L

        sut.execute(taskGroupId)

        coVerify(exactly = 1) {
            taskGroupRepositoryMock.delete(taskGroupId)
            taskRepositoryMock.deleteByTaskGroup(taskGroupId)
        }
    }

}