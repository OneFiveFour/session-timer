package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.jupiter.api.Test

class SetTaskGroupColorUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk(relaxed = true)

    private val sut = SetTaskGroupColorUseCase(
        taskGroupRepository
    )

    @Test
    fun `executing the use case sets the color to the task group`() = runTest {
        val taskGroupId = 1L
        val color = 0xF0F0F0

        sut.execute(taskGroupId, color)

        coVerify(exactly = 1) {
            taskGroupRepository.setColor(taskGroupId, color)
        }
    }
}