package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.jupiter.api.Test

class SetTaskTitleUseCaseTest {

    private val taskRepository: TaskRepository = mockk(relaxed = true)

    private val sut = SetTaskTitleUseCase(
        taskRepository
    )

    @Test
    fun `executing the use case sets the title to the task`() = runTest {
        val taskId = 1L
        val title = "Test Task Title"

        sut.execute(taskId, title)

        coVerify(exactly = 1) {
            taskRepository.setTitle(taskId, title)
        }
    }
}