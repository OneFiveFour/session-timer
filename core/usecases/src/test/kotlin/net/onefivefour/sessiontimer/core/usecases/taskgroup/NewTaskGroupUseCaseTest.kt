package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.database.test.FAKE_DB_DEFAULT_VALUES
import org.junit.Test

internal class NewTaskGroupUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk()
    private val taskRepository: TaskRepository = mockk()

    private fun sut() = NewTaskGroupUseCaseImpl(
        taskGroupRepository,
        taskRepository,
        FAKE_DB_DEFAULT_VALUES
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
                    FAKE_DB_DEFAULT_VALUES.getTaskGroupTitle(),
                    FAKE_DB_DEFAULT_VALUES.getTaskGroupColor(),
                    FAKE_DB_DEFAULT_VALUES.getTaskGroupPlayMode(),
                    FAKE_DB_DEFAULT_VALUES.getTaskGroupNumberOfRandomTasks(),
                    sessionId
                )
                taskRepository.newTask(
                    FAKE_DB_DEFAULT_VALUES.getTaskTitle(),
                    FAKE_DB_DEFAULT_VALUES.getTaskDuration(),
                    taskGroupId
                )
            }
        }
}
