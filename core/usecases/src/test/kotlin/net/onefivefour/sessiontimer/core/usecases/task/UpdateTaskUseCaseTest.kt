package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.Test


class UpdateTaskUseCaseTest {

    private val taskRepository: TaskRepository = mockk(relaxed = true)

    private val sut = UpdateTaskUseCase(
        taskRepository
    )

    @Test
    fun `executing the use case updates the task`() = runTest {
        val taskId = 1L
        val title = "New Task Title"
        val duration = 3.seconds

        sut.execute(taskId, title, duration)

        coVerify(exactly = 1) {
            taskRepository.update(taskId, title, duration)
        }
    }
}
