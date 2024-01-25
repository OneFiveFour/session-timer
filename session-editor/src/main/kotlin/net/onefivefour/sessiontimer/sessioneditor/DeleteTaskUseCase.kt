package net.onefivefour.sessiontimer.sessioneditor

import net.onefivefour.sessiontimer.database.domain.TaskRepository
import javax.inject.Inject

internal class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
){

    suspend fun execute(taskId: Long) {
        taskRepository.delete(taskId)
    }

}
