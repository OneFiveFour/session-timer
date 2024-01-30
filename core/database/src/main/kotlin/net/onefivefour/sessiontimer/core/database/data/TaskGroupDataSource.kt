package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.TaskGroup

interface TaskGroupDataSource {

    suspend fun getBySessionId(sessionId: Long): Flow<List<TaskGroup>>

    suspend fun get(taskGroupId: Long): Flow<TaskGroup>

    suspend fun deleteById(taskGroupId: Long)

    suspend fun deleteBySessionId(sessionId: Long)

    suspend fun insert(title: String, sessionId: Long)

    fun getLastInsertId(): Long
}
