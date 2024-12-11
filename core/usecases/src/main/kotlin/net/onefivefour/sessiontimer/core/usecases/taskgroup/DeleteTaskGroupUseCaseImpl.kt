package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.DeleteTaskGroupUseCase

@ViewModelScoped
class DeleteTaskGroupUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository
) : DeleteTaskGroupUseCase {

    override suspend fun execute(taskGroupId: Long) {
        taskGroupRepository.deleteTaskGroupById(taskGroupId)
        taskRepository.deleteTasksByTaskGroupId(taskGroupId)
    }
}
