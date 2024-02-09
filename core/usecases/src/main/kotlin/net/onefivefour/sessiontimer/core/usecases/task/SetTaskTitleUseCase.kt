package net.onefivefour.sessiontimer.core.usecases.task

import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDefaultValuesProvider
import javax.inject.Inject

class SetTaskTitleUseCase @Inject constructor(
    private val taskRepository: TaskRepository
){

    suspend fun execute(taskId: Long, title: String) {
        taskRepository.setTitle(taskId, title)
    }

}
