package net.onefivefour.sessiontimer.core.usecases

import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import javax.inject.Inject

class DeleteTaskGroupUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository
){

    suspend fun execute(taskGroupId: Long) {
        taskGroupRepository.delete(taskGroupId)
        taskRepository.deleteByTaskGroup(taskGroupId)
    }

}
