package net.onefivefour.sessiontimer.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.database.TaskGroup
import net.onefivefour.sessiontimer.database.TaskGroupQueries
import javax.inject.Inject

internal class TaskGroupDataSourceImpl @Inject constructor(
    private val queries: TaskGroupQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskGroupDataSource {

    override suspend fun getAll(sessionId: Long): Flow<List<TaskGroup>> {
        return withContext(dispatcher) {
            queries.getAll(sessionId).asFlow().mapToList(dispatcher)
        }
    }

    override suspend fun insert(taskGroupId: Long?, title: String, color: Long, sessionId: Long) {
        withContext(dispatcher) {
            queries.insert(taskGroupId, title, color, sessionId)
        }
    }

    override suspend fun delete(taskGroupId: Long) {
        withContext(dispatcher) {
            queries.delete(taskGroupId)
        }
    }
}