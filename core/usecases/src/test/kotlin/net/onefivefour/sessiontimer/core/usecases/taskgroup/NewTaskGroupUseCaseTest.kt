package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDatabaseDefaultValues
import org.junit.jupiter.api.Test

class NewTaskGroupUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk()
    private val taskRepository: TaskRepository = mockk()

    private val defaultValuesProvider : DatabaseDatabaseDefaultValues = mockk<DatabaseDatabaseDefaultValues>().apply {
        every { getTaskGroupTitle() } returns DEFAULT_TASK_GROUP_TITLE
        every { getTaskTitle() } returns DEFAULT_TASK_TITLE
    }

    private val sut = NewTaskGroupUseCase(
        taskGroupRepository,
        taskRepository,
        defaultValuesProvider
    )

    @Test
    fun `executing the use case creates a new task group and also the first of its tasks`() = runTest {
        val taskGroupId = 123L
        coEvery { taskGroupRepository.new(any(), any()) } returns Unit
        coEvery { taskGroupRepository.getLastInsertId() } returns taskGroupId
        coEvery { taskRepository.new(any(), any()) } returns Unit

        val sessionId = 1L

        sut.execute(sessionId)

        coVerify(exactly = 1) {
            taskGroupRepository.new(DEFAULT_TASK_GROUP_TITLE, sessionId)
            taskRepository.new(DEFAULT_TASK_TITLE, taskGroupId)
        }
    }

    companion object {
        private const val DEFAULT_TASK_GROUP_TITLE = "DEFAULT_TASK_GROUP_TITLE"
        private const val DEFAULT_TASK_TITLE = "DEFAULT_TASK_TITLE"
    }

}