package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.fakes.FakeDefaultValues
import org.junit.Test


class NewTaskGroupUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk()
    private val taskRepository: TaskRepository = mockk()

    private val sut = NewTaskGroupUseCaseImpl(
        taskGroupRepository,
        taskRepository,
        FakeDefaultValues
    )

    @Test
    fun `executing the use case creates a new task group and also the first of its tasks`() =
        runTest {
            val taskGroupId = 123L
            coEvery { taskGroupRepository.new(any(), any(), any(), any(), any()) } returns Unit
            coEvery { taskGroupRepository.getLastInsertId() } returns taskGroupId
            coEvery { taskRepository.new(any(), any(), any()) } returns Unit

            val sessionId = 1L
            sut.execute(sessionId)

            coVerify(exactly = 1) {
                taskGroupRepository.new(
                    FakeDefaultValues.getTaskGroupTitle(),
                    FakeDefaultValues.getTaskGroupColor(),
                    FakeDefaultValues.getTaskGroupPlayMode(),
                    FakeDefaultValues.getTaskGroupNumberOfRandomTasks(),
                    sessionId
                )
                taskRepository.new(
                    FakeDefaultValues.getTaskTitle(),
                    FakeDefaultValues.getTaskDuration(),
                    taskGroupId
                )
            }
        }
}
