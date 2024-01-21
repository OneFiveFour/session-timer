package net.onefivefour.sessiontimer.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.core.di.IoDispatcher
import net.onefivefour.sessiontimer.database.Task
import net.onefivefour.sessiontimer.database.TaskQueries
import javax.inject.Inject

internal class TaskDataSourceImpl @Inject constructor(
    private val queries: TaskQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TaskDataSource {

    override suspend fun getAll(taskGroupIds: List<Long>): Flow<List<Task>> {
        return withContext(dispatcher) {
            queries.getAll(taskGroupIds).asFlow().mapToList(dispatcher)
        }
    }

    override suspend fun insert(taskId: Long?, taskGroupId: Long) {
        withContext(dispatcher) {
            queries.insert(
                id = taskId,
                title = null,
                durationInSeconds = null,
                taskGroupId = taskGroupId
            )
        }
    }

    override suspend fun delete(taskId: Long) {
        withContext(dispatcher) {
            queries.delete(taskId)
        }
    }
}