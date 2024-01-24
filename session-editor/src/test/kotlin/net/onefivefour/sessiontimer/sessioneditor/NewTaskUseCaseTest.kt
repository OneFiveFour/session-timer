package net.onefivefour.sessiontimer.sessioneditor

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.database.domain.TaskRepository
import org.junit.jupiter.api.Test

class NewTaskUseCaseTest {

    private val taskRepositoryMock: TaskRepository = mockk()

    private val sut = NewTaskUseCase(
        taskRepositoryMock
    )

    @Test
    fun `executing the use case creates a new task`() = runTest {
        coEvery { taskRepositoryMock.new(any()) } returns Unit

        val taskGroupId = 1L

        sut.execute(taskGroupId)

        coVerify(exactly = 1) {
            taskRepositoryMock.new(taskGroupId)
        }
    }

}