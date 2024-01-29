package net.onefivefour.sessiontimer.core.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.sessioneditor.NewTaskGroupUseCase
import org.junit.jupiter.api.Test

class NewTaskGroupUseCaseTest {

    private val taskGroupRepositoryMock: TaskGroupRepository = mockk()
    private val taskRepositoryMock: TaskRepository = mockk()

    private val sut = net.onefivefour.sessiontimer.core.usecases.NewTaskGroupUseCase(
        taskGroupRepositoryMock,
        taskRepositoryMock
    )

    @Test
    fun `executing the use case creates a new task group and also the first of its tasks`() = runTest {
        coEvery { taskGroupRepositoryMock.new(any()) } returns Unit
        coEvery { taskGroupRepositoryMock.getLastInsertId() } returns 123L
        coEvery { taskRepositoryMock.new(any()) } returns Unit

        val sessionId = 1L

        sut.execute(sessionId)

        coVerify(exactly = 1) {
            taskGroupRepositoryMock.new(sessionId)
            taskRepositoryMock.new(123L)
        }
    }

}