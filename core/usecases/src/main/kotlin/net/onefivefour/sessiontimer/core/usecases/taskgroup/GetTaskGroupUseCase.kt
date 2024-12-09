package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.GetTaskGroupUseCase

@ViewModelScoped
class GetTaskGroupUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) : GetTaskGroupUseCase {

    override suspend fun execute(taskGroupId: Long): Flow<TaskGroup> {
        return taskGroupRepository.getById(taskGroupId)
    }
}
