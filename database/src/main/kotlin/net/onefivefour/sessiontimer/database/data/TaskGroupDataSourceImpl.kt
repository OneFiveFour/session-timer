package net.onefivefour.sessiontimer.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.core.di.IoDispatcher
import net.onefivefour.sessiontimer.database.TaskGroup
import net.onefivefour.sessiontimer.database.TaskGroupQueries
import javax.inject.Inject

internal class TaskGroupDataSourceImpl @Inject constructor(
    private val queries: TaskGroupQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TaskGroupDataSource {

    override suspend fun getAll(sessionId: Long): Flow<List<TaskGroup>> {
        return withContext(dispatcher) {
            queries.getAll(sessionId).asFlow().mapToList(dispatcher)
        }
    }

    override suspend fun insert(taskGroupId: Long?, sessionId: Long) {
        withContext(dispatcher) {
            queries.insert(
                id = taskGroupId,
                title = null,
                color = null,
                sessionId = sessionId
            )
        }
    }

    override suspend fun delete(taskGroupId: Long) {
        withContext(dispatcher) {
            queries.delete(taskGroupId)
        }
    }

    override fun getLastInsertId(): Long {
        return queries.getLastInsertRowId().executeAsOne()
    }
}