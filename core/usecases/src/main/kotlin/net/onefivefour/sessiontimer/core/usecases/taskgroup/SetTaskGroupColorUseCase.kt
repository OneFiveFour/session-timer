package net.onefivefour.sessiontimer.core.usecases.taskgroup

import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import javax.inject.Inject

class SetTaskGroupColorUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) {

    suspend fun execute(taskGroupId: Long, color: Int) {
        taskGroupRepository.setColor(taskGroupId, color)
    }
}