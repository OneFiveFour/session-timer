package net.onefivefour.sessiontimer.core.usecases.task

import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
){

    suspend fun execute(taskId: Long) {
        taskRepository.delete(taskId)
    }

}
