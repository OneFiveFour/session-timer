package net.onefivefour.sessiontimer.core.usecases

import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import javax.inject.Inject

class SetTaskGroupTitleUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) {

    suspend fun execute(taskGroupId: Long, title: String) {
        taskGroupRepository.setTitle(taskGroupId, title)
    }
}