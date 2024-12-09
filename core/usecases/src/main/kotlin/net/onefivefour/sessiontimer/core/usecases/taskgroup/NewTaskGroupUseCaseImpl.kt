package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.data.DatabaseDefaultValues
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.NewTaskGroupUseCase

@ViewModelScoped
class NewTaskGroupUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository,
    private val defaultValues: DatabaseDefaultValues
): NewTaskGroupUseCase {

    override suspend fun execute(sessionId: Long) {
        val taskGroupTitle = defaultValues.getTaskGroupTitle()
        val taskGroupColor = defaultValues.getTaskGroupColor()
        val taskGroupPlayMode = defaultValues.getTaskGroupPlayMode()
        val taskGroupNumberOfRandomTasks = defaultValues.getTaskGroupNumberOfRandomTasks()

        taskGroupRepository.new(
            taskGroupTitle,
            taskGroupColor,
            taskGroupPlayMode,
            taskGroupNumberOfRandomTasks,
            sessionId
        )

        val taskGroupId = taskGroupRepository.getLastInsertId()
        val taskTitle = defaultValues.getTaskTitle()
        val taskDuration = defaultValues.getTaskDuration()

        taskRepository.new(
            taskTitle,
            taskDuration,
            taskGroupId
        )
    }
}
