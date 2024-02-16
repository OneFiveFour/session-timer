package net.onefivefour.sessiontimer.core.usecases.taskgroup

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import org.junit.jupiter.api.Test

class UpdateTaskGroupUseCaseTest {

    private val taskGroupRepository: TaskGroupRepository = mockk(relaxed = true)

    private val sut = UpdateTaskGroupUseCase(
        taskGroupRepository
    )

    @Test
    fun `executing the use case updates the task group`() = runTest {
        val taskGroupId = 1L
        val title = "Task Group Title"
        val color = 0xF0F0F0
        val playMode = PlayMode.RANDOM
        val numberOfRandomTasks = 19

        sut.execute(
            taskGroupId,
            title,
            color,
            playMode,
            numberOfRandomTasks
        )

        coVerify(exactly = 1) {
            taskGroupRepository.update(
                taskGroupId,
                title,
                color,
                playMode,
                numberOfRandomTasks
            )
        }
    }
}