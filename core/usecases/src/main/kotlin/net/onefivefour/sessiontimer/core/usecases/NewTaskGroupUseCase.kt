package net.onefivefour.sessiontimer.core.usecases

import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import javax.inject.Inject

class NewTaskGroupUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository
){

    suspend fun execute(sessionId: Long) {
        taskGroupRepository.new(sessionId)
        val taskGroupId = taskGroupRepository.getLastInsertId()
        taskRepository.new(taskGroupId)
    }

}
