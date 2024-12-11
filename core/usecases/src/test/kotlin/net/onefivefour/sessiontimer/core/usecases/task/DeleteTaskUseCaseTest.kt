package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.Test

class DeleteTaskUseCaseTest {

    private val taskRepository: TaskRepository = mockk()

    private fun sut() = DeleteTaskUseCaseImpl(
        taskRepository
    )

    @Test
    fun `GIVEN a taskId WHEN executing the UseCase THEN the taskRepository deletes the task`() = runTest {
        // GIVEN
        coEvery { taskRepository.deleteTask(any()) } returns Unit
        val taskId = 1L

        // WHEN
        sut().execute(taskId)

        // THEN
        coVerify(exactly = 1) {
            taskRepository.deleteTask(taskId)
        }
    }
}
