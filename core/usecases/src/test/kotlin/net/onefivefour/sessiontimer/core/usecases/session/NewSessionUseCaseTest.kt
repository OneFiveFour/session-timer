package net.onefivefour.sessiontimer.core.usecases.session

import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.fakes.DatabaseDefaultValuesFake
import org.junit.Test

class NewSessionUseCaseTest {

    private val sessionRepository: SessionRepository = mockk()

    private val taskGroupRepository: TaskGroupRepository = mockk()

    private val taskRepository: TaskRepository = mockk()

    private fun sut() = NewSessionUseCaseImpl(
        sessionRepository,
        taskGroupRepository,
        taskRepository,
        DatabaseDefaultValuesFake
    )

    @Test
    fun `WHEN executing the UseCase THEN a new session with taskGroup and task is created`() =
        runTest {
            val sessionId = 1L
            val taskGroupId = 2L
            coEvery { sessionRepository.newSession(any()) } returns Unit
            coEvery { sessionRepository.getLastInsertId() } returns sessionId
            coEvery {
                taskGroupRepository.newTaskGroup(
                    title = any(),
                    color = any(),
                    playMode = any(),
                    numberOfRandomTasks = any(),
                    sessionId = any()
                )
            } returns Unit
            coEvery { taskGroupRepository.getLastInsertId() } returns taskGroupId
            coEvery { taskRepository.newTask(any(), any(), any()) } returns Unit

            // WHEN
            sut().execute()

            // THEN
            coVerify(ordering = Ordering.ORDERED) {
                sessionRepository.newSession(DatabaseDefaultValuesFake.getSessionTitle())

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
