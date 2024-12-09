package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.task.DeleteTaskUseCase

@ViewModelScoped
class DeleteTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
): DeleteTaskUseCase {

    override suspend fun execute(taskId: Long) {
        taskRepository.delete(taskId)
    }
}
