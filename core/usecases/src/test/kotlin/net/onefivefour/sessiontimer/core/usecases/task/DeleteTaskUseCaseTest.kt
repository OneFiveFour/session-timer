package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.jupiter.api.Test

class DeleteTaskUseCaseTest {

    private val taskRepository: TaskRepository = mockk()

    private val sut = DeleteTaskUseCase(
        taskRepository
    )

    @Test
    fun `executing the use case deletes the task`() = runTest {
        coEvery { taskRepository.delete(any()) } returns Unit

        val taskId = 1L

        sut.execute(taskId)

        coVerify(exactly = 1) {
            taskRepository.delete(taskId)
        }
    }
}
