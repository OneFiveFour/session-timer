package net.onefivefour.sessiontimer.core.usecases.session

import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDefaultValues
import org.junit.jupiter.api.Test

class NewSessionUseCaseTest {

    private val sessionRepository: SessionRepository = mockk()
    private val taskGroupRepository: TaskGroupRepository = mockk()
    private val taskRepository: TaskRepository = mockk()

    private val defaultValuesProvider : DatabaseDefaultValues = mockk<DatabaseDefaultValues>().apply {
        every { getSessionTitle() } returns  DEFAULT_SESSION_TITLE
        every { getTaskGroupTitle() } returns  DEFAULT_TASK_GROUP_TITLE
        every { getTaskTitle() } returns  DEFAULT_TASK_TITLE
    }

    private val sut = NewSessionUseCase(
        sessionRepository,
        taskGroupRepository,
        taskRepository,
        defaultValuesProvider
    )

    @Test
    fun `executing the use case creates a new session, taskGroup and task`() = runTest {
        val sessionId = 1L
        val taskGroupId = 2L
        coEvery { sessionRepository.new(any()) } returns Unit
        coEvery { sessionRepository.getLastInsertId() } returns sessionId
        coEvery { taskGroupRepository.new(any(), any()) } returns Unit
        coEvery { taskGroupRepository.getLastInsertId() } returns taskGroupId
        coEvery { taskRepository.new(any(), any()) } returns Unit

        sut.execute()

        coVerify(ordering = Ordering.ORDERED) {
            sessionRepository.new(DEFAULT_SESSION_TITLE)
            taskGroupRepository.new(DEFAULT_TASK_GROUP_TITLE, sessionId)
            taskRepository.new(DEFAULT_TASK_TITLE, taskGroupId)
        }
    }

    companion object {
        private const val DEFAULT_SESSION_TITLE = "DEFAULT_SESSION_TITLE"
        private const val DEFAULT_TASK_GROUP_TITLE = "DEFAULT_TASK_GROUP_TITLE"
        private const val DEFAULT_TASK_TITLE = "DEFAULT_TASK_TITLE"
    }
}