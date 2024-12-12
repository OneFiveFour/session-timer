package net.onefivefour.sessiontimer.core.database.domain

import kotlin.time.Duration
import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.Task

interface TaskRepository {
    suspend fun newTask(title: String, durationInSeconds: Int, taskGroupId: Long)

    suspend fun getTasksByTaskGroupIds(taskGroupIds: List<Long>): Flow<List<Task>>

    suspend fun updateTask(taskId: Long, title: String, duration: Duration)

    suspend fun deleteTask(taskId: Long)

    suspend fun deleteTasksByTaskGroupId(taskGroupId: Long)
}
