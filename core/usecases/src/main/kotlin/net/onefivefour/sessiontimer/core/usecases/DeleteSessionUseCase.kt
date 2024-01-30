package net.onefivefour.sessiontimer.core.usecases

import kotlinx.coroutines.flow.first
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import javax.inject.Inject

class DeleteSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val taskGroupsRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository,
){

    suspend fun execute(sessionId: Long) {
        val taskGroups = taskGroupsRepository.getBySessionId(sessionId).first()

        taskGroups.forEach { taskGroup ->
            taskRepository.deleteByTaskGroup(taskGroup.id)
            taskGroupsRepository.deleteById(taskGroup.id)
        }

        sessionRepository.deleteById(sessionId)
    }

}
