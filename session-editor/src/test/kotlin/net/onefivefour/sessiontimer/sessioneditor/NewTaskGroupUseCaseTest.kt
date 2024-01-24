package net.onefivefour.sessiontimer.sessioneditor

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.database.domain.TaskRepository
import org.junit.jupiter.api.Test

class NewTaskGroupUseCaseTest {

    private val taskGroupRepositoryMock: TaskGroupRepository = mockk()
    private val taskRepositoryMock: TaskRepository = mockk()

    private val sut = NewTaskGroupUseCase(
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