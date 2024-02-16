package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.TaskGroup
import net.onefivefour.sessiontimer.core.database.TaskGroupQueries
import net.onefivefour.sessiontimer.core.di.IoDispatcher

internal class TaskGroupDataSourceImpl @Inject constructor(
    private val queries: TaskGroupQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TaskGroupDataSource {

    override suspend fun insert(
        title: String,
        color: Long,
        playMode: String,
        numberOfRandomTasks: Long,
        sessionId: Long
    ) {
        withContext(dispatcher) {
            queries.new(
                id = null,
                title = title,
                color = color,
                playMode = playMode,
                numberOfRandomTasks = numberOfRandomTasks,
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

    override suspend fun update(
        taskGroupId: Long,
        title: String,
        color: Long,
        playMode: String,
        numberOfRandomTasks: Long
    ) {
        withContext(dispatcher) {
            queries.update(
                title,
                color,
                playMode,
                numberOfRandomTasks,
                taskGroupId
            )
        }
    }

    override suspend fun deleteBySessionId(sessionId: Long) {
        withContext(dispatcher) {
            queries.deleteBySessionId(sessionId)
        }
    }

    override fun getLastInsertId(): Long {
        return queries.getLastInsertRowId().executeAsOne()
    }
}
