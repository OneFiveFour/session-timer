package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.Task

interface TaskDataSource {

    suspend fun insert(title: String, taskGroupId: Long)

    suspend fun getByTaskGroupIds(taskGroupIds: List<Long>): Flow<List<Task>>

    suspend fun deleteById(taskId: Long)

    suspend fun deleteByTaskGroupId(taskGroupId: Long)

    suspend fun deleteByIds(taskIds: List<Long>)

    suspend fun setDurationInSeconds(taskId: Long, durationInSeconds: Long)

    suspend fun setTitle(taskId: Long, title: String)
}
