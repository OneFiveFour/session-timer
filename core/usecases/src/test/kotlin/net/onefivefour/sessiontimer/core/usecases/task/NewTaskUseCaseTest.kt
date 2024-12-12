package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.database.test.FAKE_DB_DEFAULT_VALUES
import org.junit.Test

internal class NewTaskUseCaseTest {

    private val taskRepository: TaskRepository = mockk()

    private fun sut() = NewTaskUseCaseImpl(
        taskRepository,
        FAKE_DB_DEFAULT_VALUES
    )

    @Test
    fun `GIVEN a taskGroupId WHEN executing the UseCase THEN the taskRepository creates a new task`() =
        runTest {
            // GIVEN
            coEvery { taskRepository.newTask(any(), any(), any()) } returns Unit
            val taskGroupId = 1L

            // WHEN
            sut().execute(taskGroupId)

            // THEN
            coVerify(exactly = 1) {
                taskRepository.newTask(
                    FAKE_DB_DEFAULT_VALUES.getTaskTitle(),
                    FAKE_DB_DEFAULT_VALUES.getTaskDuration(),
                    taskGroupId
                )
            }
        }
}
