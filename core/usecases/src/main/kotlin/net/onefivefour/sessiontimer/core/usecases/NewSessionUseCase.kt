package net.onefivefour.sessiontimer.core.usecases

import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDefaultValuesProvider
import javax.inject.Inject

class NewSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository,
    private val defaultValuesProvider: DatabaseDefaultValuesProvider
) {

    suspend fun execute() {
        val defaultSessionTitle = defaultValuesProvider.getSessionTitle()
        sessionRepository.new(defaultSessionTitle)

        val lastInsertSessionId = sessionRepository.getLastInsertId()
        val defaultTaskGroupTitle = defaultValuesProvider.getTaskGroupTitle()
        taskGroupRepository.new(defaultTaskGroupTitle, lastInsertSessionId)

        val lastInsertTaskGroupId = taskGroupRepository.getLastInsertId()
        val defaultTaskTitle = defaultValuesProvider.getTaskTitle()
        taskRepository.new(defaultTaskTitle, lastInsertTaskGroupId)
    }

}
