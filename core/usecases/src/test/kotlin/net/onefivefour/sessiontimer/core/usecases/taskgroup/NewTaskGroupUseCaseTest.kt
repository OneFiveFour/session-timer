package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.fakes.DatabaseDefaultValuesFake
import org.junit.Test

class NewTaskGroupUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk()
    private val taskRepository: TaskRepository = mockk()

    private fun sut() = NewTaskGroupUseCaseImpl(
        taskGroupRepository,
        taskRepository,
        DatabaseDefaultValuesFake
    )

    @Test
    fun `GIVEN a sessionId WHEN executing the UseCase THEN it creates a new task group and the first of its tasks`() =
        runTest {
            // GIVEN
            val sessionId = 1L
            val taskGroupId = 123L
            coEvery {
                taskGroupRepository.newTaskGroup(
                    title = any(),
                    color = any(),
                    playMode = any(),
                    numberOfRandomTasks = any(),
                    sessionId = any()
                )
            } returns Unit

            coEvery {
                taskGroupRepository.getLastInsertId()
            } returns taskGroupId

            coEvery {
                taskRepository.newTask(
                    title = any(),
                    durationInSeconds = any(),
                    taskGroupId = any()
                )
            } returns Unit

            // WHEN
            sut().execute(sessionId)

            // THEN
            coVerify(exactly = 1) {
                taskGroupRepository.newTaskGroup(
                    DatabaseDefaultValuesFake.getTaskGroupTitle(),
                    DatabaseDefaultValuesFake.getTaskGroupColor(),
                    DatabaseDefaultValuesFake.getTaskGroupPlayMode(),
                    DatabaseDefaultValuesFake.getTaskGroupNumberOfRandomTasks(),
                    sessionId
                )
                taskRepository.newTask(
                    DatabaseDefaultValuesFake.getTaskTitle(),
                    DatabaseDefaultValuesFake.getTaskDuration(),
                    taskGroupId
                )
            }
        }
}
