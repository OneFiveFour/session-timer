package net.onefivefour.sessiontimer.feature.sessioneditor

import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import javax.inject.Inject

internal class NewTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
){

    suspend fun execute(taskGroupId: Long) {
        taskRepository.new(taskGroupId)
    }

}
