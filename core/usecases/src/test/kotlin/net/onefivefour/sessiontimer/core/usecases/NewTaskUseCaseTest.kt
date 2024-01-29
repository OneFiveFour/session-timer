package net.onefivefour.sessiontimer.core.usecases

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.sessioneditor.NewTaskUseCase
import org.junit.jupiter.api.Test

class NewTaskUseCaseTest {

    private val taskRepositoryMock: TaskRepository = mockk()

    private val sut = net.onefivefour.sessiontimer.core.usecases.NewTaskUseCase(
        taskRepositoryMock
    )

    @Test
    fun `executing the use case creates a new task`() = runTest {
        coEvery { taskRepositoryMock.new(any()) } returns Unit

        val taskGroupId = 1L

        sut.execute(taskGroupId)

        coVerify(exactly = 1) {
            taskRepositoryMock.new(taskGroupId)
        }
    }

}