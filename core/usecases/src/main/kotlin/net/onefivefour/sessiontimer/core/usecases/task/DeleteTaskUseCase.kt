package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import javax.inject.Inject

@ViewModelScoped
class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
){

    suspend fun execute(taskId: Long) {
        taskRepository.delete(taskId)
    }

}
