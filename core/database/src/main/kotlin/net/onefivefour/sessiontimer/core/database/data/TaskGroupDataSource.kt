package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.TaskGroup

interface TaskGroupDataSource {

    suspend fun insert(title: String, color: Long, sessionId: Long)

    suspend fun getById(taskGroupId: Long): Flow<TaskGroup>

    suspend fun getBySessionId(sessionId: Long): Flow<List<TaskGroup>>

    suspend fun deleteById(taskGroupId: Long)

    suspend fun deleteBySessionId(sessionId: Long)

    suspend fun setTitle(taskGroupId: Long, title: String)

    suspend fun setColor(taskGroupId: Long, color: Long)

    fun getLastInsertId(): Long
}
