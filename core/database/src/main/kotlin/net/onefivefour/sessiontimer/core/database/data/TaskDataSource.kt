package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.Task

interface TaskDataSource {

    suspend fun getAll(taskGroupIds: List<Long>): Flow<List<Task>>

    suspend fun delete(taskId: Long)

    suspend fun deleteByTaskGroup(taskGroupId: Long)

    suspend fun insert(taskGroupId: Long)
}
