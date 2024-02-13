package net.onefivefour.sessiontimer.core.usecases.task

import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDatabaseDefaultValues
import javax.inject.Inject

class NewTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val defaultValuesProvider: DatabaseDatabaseDefaultValues
){

    suspend fun execute(taskGroupId: Long) {
        val defaultTitle = defaultValuesProvider.getTaskTitle()
        taskRepository.new(defaultTitle, taskGroupId)
    }

}
