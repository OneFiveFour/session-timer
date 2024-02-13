package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDefaultValues
import javax.inject.Inject

@ViewModelScoped
class NewTaskGroupUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository,
    private val defaultValues: DatabaseDefaultValues
){

    suspend fun execute(sessionId: Long) {
        val taskGroupTitle = defaultValues.getTaskGroupTitle()
        val taskGroupColor = defaultValues.getTaskGroupColor()
        taskGroupRepository.new(taskGroupTitle, taskGroupColor, sessionId)

        val taskGroupId = taskGroupRepository.getLastInsertId()
        val taskTitle = defaultValues.getTaskTitle()
        val taskDuration = defaultValues.getTaskDuration()
        taskRepository.new(taskTitle, taskDuration, taskGroupId)
    }

}
