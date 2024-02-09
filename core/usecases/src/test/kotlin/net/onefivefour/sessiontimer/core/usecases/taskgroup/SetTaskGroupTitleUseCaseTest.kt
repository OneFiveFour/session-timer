package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.jupiter.api.Test

class SetTaskGroupTitleUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk(relaxed = true)

    private val sut = SetTaskGroupTitleUseCase(
        taskGroupRepository
    )

    @Test
    fun `executing the use case sets the title to the task group`() = runTest {
        val taskGroupId = 1L
        val title = "Test TaskGroup Title"

        sut.execute(taskGroupId, title)

        coVerify(exactly = 1) {
            taskGroupRepository.setTitle(taskGroupId, title)
        }
    }
}