package net.onefivefour.sessiontimer.core.usecases.session

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDefaultValues
import javax.inject.Inject

@ViewModelScoped
class NewSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository,
    private val defaultValues: DatabaseDefaultValues
) {

    suspend fun execute() {
        val defaultSessionTitle = defaultValues.getSessionTitle()
        sessionRepository.new(defaultSessionTitle)

        val sessionId = sessionRepository.getLastInsertId()
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
        taskRepository.new(taskTitle, taskDuration, taskGroupId)
    }

}
