package net.onefivefour.sessiontimer.core.usecases

import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import javax.inject.Inject

class NewTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
){

    suspend fun execute(taskGroupId: Long) {
        taskRepository.new(taskGroupId)
    }

}
