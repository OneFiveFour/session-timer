package net.onefivefour.sessiontimer.sessioneditor

import net.onefivefour.sessiontimer.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.database.domain.TaskRepository
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