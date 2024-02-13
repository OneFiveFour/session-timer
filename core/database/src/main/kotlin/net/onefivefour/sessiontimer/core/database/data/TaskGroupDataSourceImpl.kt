package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.core.database.TaskGroup
import net.onefivefour.sessiontimer.core.database.TaskGroupQueries
import net.onefivefour.sessiontimer.core.di.IoDispatcher

internal class TaskGroupDataSourceImpl @Inject constructor(
    private val queries: TaskGroupQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TaskGroupDataSource {

    override suspend fun insert(title: String, sessionId: Long) {
        withContext(dispatcher) {
            queries.insert(
                id = null,
                title = title,
                color = null,
                sessionId = sessionId
            )
        }
    }

    override suspend fun getById(taskGroupId: Long): Flow<TaskGroup> {
        return withContext(dispatcher) {
            queries.getById(taskGroupId).asFlow().mapToOne(dispatcher)
        }
    }

    override suspend fun getBySessionId(sessionId: Long): Flow<List<TaskGroup>> {
        return withContext(dispatcher) {
            queries.getBySessionId(sessionId).asFlow().mapToList(dispatcher)
        }
    }

    override suspend fun deleteById(taskGroupId: Long) {
        withContext(dispatcher) {
            queries.deleteById(taskGroupId)
        }
    }

    override suspend fun deleteBySessionId(sessionId: Long) {
        withContext(dispatcher) {
            queries.deleteBySessionId(sessionId)
        }
    }

    override suspend fun setTitle(taskGroupId: Long, title: String) {
        withContext(dispatcher) {
            queries.setTitle(title, taskGroupId)
        }
    }

    override suspend fun setColor(taskGroupId: Long, color: Long) {
        withContext(dispatcher) {
            queries.setColor(color, taskGroupId)
        }
    }

    override fun getLastInsertId(): Long {
        return queries.getLastInsertRowId().executeAsOne()
    }
}
