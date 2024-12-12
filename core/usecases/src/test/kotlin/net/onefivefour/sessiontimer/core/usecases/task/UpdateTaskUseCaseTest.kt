package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.Test

internal class UpdateTaskUseCaseTest {

    private val taskRepository: TaskRepository = mockk(relaxed = true)

    private fun sut() = UpdateTaskUseCaseImpl(
        taskRepository
    )

    @Test
    fun `GIVEN task data WHEN executing the UseCase THEN the taskRepository updates the task`() =
        runTest {
            // GIVEN
            val taskId = 1L
            val title = "New Task Title"
            val duration = 3.seconds

            // WHEN
            sut().execute(taskId, title, duration)

            // THEN
            coVerify(exactly = 1) {
                taskRepository.updateTask(taskId, title, duration)
            }
        }
}
