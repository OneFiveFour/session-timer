package net.onefivefour.sessiontimer.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.database.Task
import net.onefivefour.sessiontimer.database.TaskQueries
import javax.inject.Inject

internal class TaskDataSourceImpl @Inject constructor(
    private val queries: TaskQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskDataSource {

    override suspend fun getAll(taskGroupId: Long): Flow<List<Task>> {
        return withContext(dispatcher) {
            queries.getAll(taskGroupId).asFlow().mapToList(dispatcher)
        }
    }

    override suspend fun insert(taskId: Long?, title: String, taskGroupId: Long) {
        withContext(dispatcher) {
            queries.insert(taskId, title, taskGroupId)
        }
    }

    override suspend fun delete(taskId: Long) {
        withContext(dispatcher) {
            queries.delete(taskId)
        }
    }
}