package net.onefivefour.sessiontimer.core.usecases.taskgroup

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import javax.inject.Inject

class GetTaskGroupUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) {

    suspend fun execute(taskGroupId: Long): Flow<TaskGroup> {
        return taskGroupRepository.get(taskGroupId)
    }

}
