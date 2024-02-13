package net.onefivefour.sessiontimer.core.usecases.task

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDefaultValues
import org.junit.jupiter.api.Test

class NewTaskUseCaseTest {

    private val taskRepository: TaskRepository = mockk()

    private val defaultValuesProvider : DatabaseDefaultValues = mockk<DatabaseDefaultValues>().apply {
        every { getTaskTitle() } returns DEFAULT_TITLE
    }

    private val sut = NewTaskUseCase(
        taskRepository,
        defaultValuesProvider
    )

    @Test
    fun `executing the use case creates a new task`() = runTest {
        coEvery { taskRepository.new(any(), any()) } returns Unit

        val taskGroupId = 1L

        sut.execute(taskGroupId)

        coVerify(exactly = 1) {
            taskRepository.new(DEFAULT_TITLE, taskGroupId)
        }
    }

    companion object {
        private const val DEFAULT_TITLE = "DEFAULT_TITLE"
    }

}