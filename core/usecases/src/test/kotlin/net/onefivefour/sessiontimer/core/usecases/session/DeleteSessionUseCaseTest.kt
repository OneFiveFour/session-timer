package net.onefivefour.sessiontimer.core.usecases.session

import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteSessionUseCaseTest {

    private val sessionRepository: SessionRepository = mockk(relaxed = true)

    private val taskGroupRepository: TaskGroupRepository = mockk(relaxed = true)

    private val taskRepository: TaskRepository = mockk(relaxed = true)

    private fun sut() = DeleteSessionUseCaseImpl(
        sessionRepository,
        taskGroupRepository,
        taskRepository
    )

    @Test
    fun `GIVEN a full session WHEN executing the UseCase THEN the session, all its taskGroups and tasks are deleted`() = runTest {
        // GIVEN
        val sessionId = 1L
        val taskGroupId1 = 2L
        val taskGroupId2 = 6L
        coEvery { taskGroupRepository.getTaskGroupBySessionId(any()) } returns flowOf(
            listOf(
                TaskGroup(
                    id = taskGroupId1,
                    title = "Task Group Title",
                    color = 0x00FF00,
                    playMode = PlayMode.RANDOM_SINGLE_TASK,
                    tasks = listOf(
                        Task(3L, "Task Title", 1.seconds, taskGroupId1),
                        Task(4L, "Task Title 2", 2.seconds, taskGroupId1),
                        Task(5L, "Task Title 3", 3.seconds, taskGroupId1)
                    ),
                    numberOfRandomTasks = 5,
                    sessionId = sessionId
                ),
                TaskGroup(
                    id = taskGroupId2,
                    title = "Task Group Title 2",
                    color = 0x00FFFF,
                    playMode = PlayMode.SEQUENCE,
                    tasks = listOf(
                        Task(7L, "Task Title 7", 7.seconds, taskGroupId2),
                        Task(8L, "Task Title 8", 8.seconds, taskGroupId2),
                        Task(9L, "Task Title 9", 9.seconds, taskGroupId2)
                    ),
                    numberOfRandomTasks = 3,
                    sessionId = sessionId
                )
            )
        )

        // WHEN
        sut().execute(sessionId)
        advanceUntilIdle()

        // THEN
        coVerify(ordering = Ordering.SEQUENCE) {
            taskGroupRepository.getTaskGroupBySessionId(sessionId)

            taskRepository.deleteTasksByTaskGroupId(taskGroupId1)
            taskGroupRepository.deleteTaskGroupById(taskGroupId1)

            taskRepository.deleteTasksByTaskGroupId(taskGroupId2)
            taskGroupRepository.deleteTaskGroupById(taskGroupId2)

            sessionRepository.deleteSessionById(sessionId)
        }
    }
}
