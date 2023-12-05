package net.onefivefour.sessiontimer.database

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.database.Task

interface TaskDataSource {

    suspend fun getAll(taskGroupId: Long): Flow<List<Task>>

    suspend fun delete(taskId: Long)

    suspend fun insert(taskId: Long?, title: String, taskGroupId: Long)
}
