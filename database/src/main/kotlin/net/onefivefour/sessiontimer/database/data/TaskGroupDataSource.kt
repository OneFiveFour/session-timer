package net.onefivefour.sessiontimer.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.database.TaskGroup

interface TaskGroupDataSource {

    suspend fun getAll(sessionId: Long): Flow<List<TaskGroup>>

    suspend fun delete(taskGroupId: Long)

    suspend fun insert(taskGroupId: Long?, title: String, color: Long, sessionId: Long)
}
