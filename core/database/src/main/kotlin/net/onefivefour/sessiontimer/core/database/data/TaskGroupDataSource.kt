package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.TaskGroup

interface TaskGroupDataSource {

    suspend fun insert(
        title: String,
        color: Long,
        playMode: String,
        numberOfRandomTasks: Long,
        sessionId: Long
    )

    suspend fun getById(taskGroupId: Long): Flow<TaskGroup>

    suspend fun getBySessionId(sessionId: Long): Flow<List<TaskGroup>>

    suspend fun deleteById(taskGroupId: Long)

    suspend fun update(
        taskGroupId: Long,
        title: String,
        color: Long,
        playMode: String,
        numberOfRandomTasks: Long
    )

    suspend fun deleteBySessionId(sessionId: Long)

    fun getLastInsertId(): Long
}
