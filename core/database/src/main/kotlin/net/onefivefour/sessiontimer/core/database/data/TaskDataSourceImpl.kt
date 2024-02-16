package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.core.database.Task
import net.onefivefour.sessiontimer.core.database.TaskQueries
import net.onefivefour.sessiontimer.core.di.IoDispatcher

internal class TaskDataSourceImpl @Inject constructor(
    private val queries: TaskQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : TaskDataSource {

    override suspend fun insert(title: String, durationInSeconds: Long, taskGroupId: Long) {
        withContext(dispatcher) {
            queries.new(
                id = null,
                title = title,
                durationInSeconds = durationInSeconds,
                taskGroupId = taskGroupId
            )
        }
    }

    override suspend fun getByTaskGroupIds(taskGroupIds: List<Long>) = queries
        .getByTaskGroupIds(taskGroupIds)
        .asFlow()
        .mapToList(dispatcher)

    override suspend fun deleteById(taskId: Long) {
        withContext(dispatcher) {
            queries.deleteById(taskId)
        }
    }

    override suspend fun update(taskId: Long, title: String, durationInSeconds: Long) {
        withContext(dispatcher) {
            queries.update(title, durationInSeconds, taskId)
        }
    }

    override suspend fun deleteByIds(taskIds: List<Long>) {
        withContext(dispatcher) {
            queries.deleteByIds(taskIds)
        }
    }

    override suspend fun deleteByTaskGroupId(taskGroupId: Long) {
        withContext(dispatcher) {
            queries.deleteByTaskGroupId(taskGroupId)
        }
    }
}
