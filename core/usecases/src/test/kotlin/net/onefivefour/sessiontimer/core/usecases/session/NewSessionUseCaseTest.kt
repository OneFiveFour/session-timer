package net.onefivefour.sessiontimer.core.usecases.session

import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.fakes.FakeDefaultValues
import org.junit.Test


class NewSessionUseCaseTest {

    private val sessionRepository: SessionRepository = mockk()
    private val taskGroupRepository: TaskGroupRepository = mockk()
    private val taskRepository: TaskRepository = mockk()

    private val sut = NewSessionUseCase(
        sessionRepository,
        taskGroupRepository,
        taskRepository,
        FakeDefaultValues
    )

    @Test
    fun `executing the use case creates a new session, taskGroup and task`() = runTest {
        val sessionId = 1L
        val taskGroupId = 2L
        coEvery { sessionRepository.new(any()) } returns Unit
        coEvery { sessionRepository.getLastInsertId() } returns sessionId
        coEvery { taskGroupRepository.new(any(), any(), any(), any(), any()) } returns Unit
        coEvery { taskGroupRepository.getLastInsertId() } returns taskGroupId
        coEvery { taskRepository.new(any(), any(), any()) } returns Unit

        sut.execute()

        coVerify(ordering = Ordering.ORDERED) {
            sessionRepository.new(FakeDefaultValues.getSessionTitle())

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
