package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository

@ViewModelScoped
class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend fun execute(taskId: Long) {
        taskRepository.delete(taskId)
    }
}
