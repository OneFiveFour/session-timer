package net.onefivefour.sessiontimer.core.database.domain

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup

interface TaskGroupRepository {
    suspend fun newTaskGroup(
        title: String,
        color: Long,
        playMode: PlayMode,
        numberOfRandomTasks: Int,
        sessionId: Long
    )

    suspend fun getTaskGroupById(taskGroupId: Long): Flow<TaskGroup>

    suspend fun getTaskGroupBySessionId(sessionId: Long): Flow<List<TaskGroup>>

    suspend fun updateTaskGroup(
        taskGroupId: Long,
        title: String,
        color: Int,
        playMode: PlayMode,
        numberOfRandomTasks: Int
    )

    suspend fun deleteTaskGroupById(taskGroupId: Long)

    fun getLastInsertId(): Long
}
