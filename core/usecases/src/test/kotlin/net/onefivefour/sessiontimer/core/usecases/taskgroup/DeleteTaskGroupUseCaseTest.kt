package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.Test

internal class DeleteTaskGroupUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk()

    private val taskRepository: TaskRepository = mockk()

    private fun sut() = DeleteTaskGroupUseCaseImpl(
        taskGroupRepository,
        taskRepository
    )

    @Test
    fun `GIVEN a taskGroupId WHEN executing the UseCase THEN both repositories delete the task group and all of its tasks`() =
        runTest {
            // GIVEN
            coEvery { taskGroupRepository.deleteTaskGroupById(any()) } returns Unit
            coEvery { taskRepository.deleteTasksByTaskGroupId(any()) } returns Unit
            val taskGroupId = 1L

            // WHEN
            sut().execute(taskGroupId)

            // THEN
            coVerify(exactly = 1) {
                taskGroupRepository.deleteTaskGroupById(taskGroupId)
                taskRepository.deleteTasksByTaskGroupId(taskGroupId)
            }
        }
}
