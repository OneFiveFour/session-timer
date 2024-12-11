package net.onefivefour.sessiontimer.core.usecases.api.taskgroup

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup

interface GetTaskGroupUseCase {
    suspend fun execute(taskGroupId: Long): Flow<TaskGroup>
}
