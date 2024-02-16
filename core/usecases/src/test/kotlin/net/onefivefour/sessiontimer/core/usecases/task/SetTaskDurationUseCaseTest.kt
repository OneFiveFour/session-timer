package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.jupiter.api.Test

class SetTaskDurationUseCaseTest {

    private val taskRepository: TaskRepository = mockk(relaxed = true)

    private val sut = UpdateTaskUseCase(
        taskRepository
    )

    @Test
    fun `executing the use case sets the duration to the task`() = runTest {
        val taskId = 1L
        val duration = 3L

        sut.execute(taskId, duration, task.durationInSeconds)

        coVerify(exactly = 1) {
            taskRepository.setDurationInSeconds(taskId, duration)
        }
    }
}