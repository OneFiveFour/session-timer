package net.onefivefour.sessiontimer.core.usecases.session

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.session.DeleteSessionUseCase

@ViewModelScoped
class DeleteSessionUseCaseImpl @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository
) : DeleteSessionUseCase {

    override suspend fun execute(sessionId: Long) {
        val taskGroups = taskGroupRepository.getBySessionId(sessionId).first()

        taskGroups.forEach { taskGroup ->
            taskRepository.deleteByTaskGroupId(taskGroup.id)
            taskGroupRepository.deleteById(taskGroup.id)
        }

        sessionRepository.deleteById(sessionId)
    }
}
