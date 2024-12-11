package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.fakes.DatabaseDefaultValuesFake
import org.junit.Test

class NewTaskUseCaseTest {

    private val taskRepository: TaskRepository = mockk()

    private val sut = NewTaskUseCaseImpl(
        taskRepository,
        DatabaseDefaultValuesFake
    )

    @Test
    fun `executing the use case creates a new task`() = runTest {
        coEvery { taskRepository.newTask(any(), any(), any()) } returns Unit

        val taskGroupId = 1L
        sut.execute(taskGroupId)

        coVerify(exactly = 1) {
            taskRepository.newTask(
                DatabaseDefaultValuesFake.getTaskTitle(),
                DatabaseDefaultValuesFake.getTaskDuration(),
                taskGroupId
            )
        }
    }
}
