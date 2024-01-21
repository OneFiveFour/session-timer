package net.onefivefour.sessiontimer.sessioneditor

import net.onefivefour.sessiontimer.database.domain.TaskRepository
import javax.inject.Inject

class NewTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
){

    suspend fun execute(taskGroupId: Long) {
        taskRepository.new(taskGroupId)
    }

}
