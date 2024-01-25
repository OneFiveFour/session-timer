package net.onefivefour.sessiontimer.taskgroupeditor

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.model.TaskGroup
import javax.inject.Inject

internal class GetTaskGroupUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) {

    suspend fun execute(taskGroupId: Long): Flow<TaskGroup> {
        return taskGroupRepository.get(taskGroupId)
    }

}
