package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.TaskGroup

interface TaskGroupDataSource {

    suspend fun getAll(sessionId: Long): Flow<List<TaskGroup>>

    suspend fun get(taskGroupId: Long): Flow<TaskGroup>

    suspend fun delete(taskGroupId: Long)

    suspend fun insert(sessionId: Long)

    fun getLastInsertId(): Long
}