package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.task.UpdateTaskUseCase

@ViewModelScoped
class UpdateTaskUseCaseImpl @Inject constructor(
    private val taskRepository: TaskRepository
): UpdateTaskUseCase {

    override suspend fun execute(taskId: Long, title: String, duration: Duration) {
        taskRepository.update(taskId, title, duration)
    }
}
