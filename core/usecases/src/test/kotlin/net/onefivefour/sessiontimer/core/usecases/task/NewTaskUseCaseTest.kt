package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.fakes.FakeDefaultValues
import org.junit.jupiter.api.Test

class NewTaskUseCaseTest {

    private val taskRepository: TaskRepository = mockk()

    private val sut = NewTaskUseCase(
        taskRepository,
        FakeDefaultValues
    )

    @Test
    fun `executing the use case creates a new task`() = runTest {
        coEvery { taskRepository.new(any(), any(), any()) } returns Unit

        val taskGroupId = 1L
        sut.execute(taskGroupId)

        coVerify(exactly = 1) {
            taskRepository.new(
                FakeDefaultValues.getTaskTitle(),
                FakeDefaultValues.getTaskDuration(),
                taskGroupId
            )
        }
    }

}