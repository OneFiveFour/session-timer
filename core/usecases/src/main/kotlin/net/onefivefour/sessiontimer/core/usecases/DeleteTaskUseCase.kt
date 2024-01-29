package net.onefivefour.sessiontimer.core.usecases

import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
){

    suspend fun execute(taskId: Long) {
        taskRepository.delete(taskId)
    }

}
