package net.onefivefour.sessiontimer.core.usecases.taskgroup

import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDatabaseDefaultValues
import javax.inject.Inject

class NewTaskGroupUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository,
    private val defaultValuesProvider: DatabaseDatabaseDefaultValues
){

    suspend fun execute(sessionId: Long) {
        val defaultTaskGroupTitle = defaultValuesProvider.getTaskGroupTitle()
        taskGroupRepository.new(defaultTaskGroupTitle, sessionId)
        val taskGroupId = taskGroupRepository.getLastInsertId()

        val defaultTaskTitle = defaultValuesProvider.getTaskTitle()
        taskRepository.new(defaultTaskTitle, taskGroupId)
    }

}
