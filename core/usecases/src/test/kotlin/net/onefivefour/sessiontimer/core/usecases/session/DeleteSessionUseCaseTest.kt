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
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteSessionUseCaseTest {

    private val sessionRepository: SessionRepository = mockk(relaxed = true)
    private val taskGroupRepository: TaskGroupRepository = mockk(relaxed = true)
    private val taskRepository: TaskRepository = mockk(relaxed = true)

    private val sut = DeleteSessionUseCase(
        sessionRepository,
        taskGroupRepository,
        taskRepository
    )

    @Test
    fun `executing the use case deletes the session and all its taskgroups and tasks`() = runTest {
        val sessionId = 1L
        val taskGroupId1 = 2L
        val taskGroupId2 = 6L
        coEvery { taskGroupRepository.getBySessionId(any()) } returns flowOf(
            listOf(
                TaskGroup(
                    taskGroupId1,
                    "Task Group Title",
                    0x00FF00,
                    PlayMode.RANDOM,
                    5,
                    listOf(
                        Task(3L, "Task Title", 1.seconds, taskGroupId1),
                        Task(4L, "Task Title 2", 2.seconds, taskGroupId1),
                        Task(5L, "Task Title 3", 3.seconds, taskGroupId1)
                    ),
                    sessionId
                ),
                TaskGroup(
                    taskGroupId2,
                    "Task Group Title 2",
                    0x00FFFF,
                    PlayMode.SEQUENCE,
                    3,
                    listOf(
                        Task(7L, "Task Title 7", 7.seconds, taskGroupId2),
                        Task(8L, "Task Title 8", 8.seconds, taskGroupId2),
                        Task(9L, "Task Title 9", 9.seconds, taskGroupId2)
                    ),
                    sessionId
                )
            )
        )

        sut.execute(sessionId)
        advanceUntilIdle()

        coVerify(ordering = Ordering.SEQUENCE) {
            taskGroupRepository.getBySessionId(sessionId)

            taskRepository.deleteByTaskGroupId(taskGroupId1)
            taskGroupRepository.deleteById(taskGroupId1)

            taskRepository.deleteByTaskGroupId(taskGroupId2)
            taskGroupRepository.deleteById(taskGroupId2)

            sessionRepository.deleteById(sessionId)
        }
    }
}
