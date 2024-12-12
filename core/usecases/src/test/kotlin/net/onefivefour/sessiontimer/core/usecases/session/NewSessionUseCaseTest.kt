package net.onefivefour.sessiontimer.core.usecases.session

import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.database.test.FAKE_DB_DEFAULT_VALUES
import org.junit.Test

internal class NewSessionUseCaseTest {

    private val sessionRepository: SessionRepository = mockk()

    private val taskGroupRepository: TaskGroupRepository = mockk()

    private val taskRepository: TaskRepository = mockk()

    private fun sut() = NewSessionUseCaseImpl(
        sessionRepository,
        taskGroupRepository,
        taskRepository,
        FAKE_DB_DEFAULT_VALUES
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
                sessionRepository.newSession(FAKE_DB_DEFAULT_VALUES.getSessionTitle())

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
