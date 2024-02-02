package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.Task

interface TaskDataSource {

    suspend fun getAll(taskGroupIds: List<Long>): Flow<List<Task>>

    suspend fun deleteById(taskId: Long)

    suspend fun deleteByTaskGroup(taskGroupId: Long)

    suspend fun deleteByIds(taskIds: List<Long>)

    suspend fun insert(title: String, taskGroupId: Long)

    suspend fun setDuration(durationInSeconds: Long, taskId: Long)

    suspend fun setTitle(title: String, taskId: Long)
}
