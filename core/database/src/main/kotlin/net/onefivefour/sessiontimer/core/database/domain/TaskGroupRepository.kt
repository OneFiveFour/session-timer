package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup as DomainTaskGroup
import net.onefivefour.sessiontimer.core.database.TaskGroup as DatabaseTaskGroup
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSource

class TaskGroupRepository @Inject constructor(
    private val taskGroupDataSource: TaskGroupDataSource
) {

    suspend fun getAll(sessionId: Long) = taskGroupDataSource
        .getBySessionId(sessionId)
        .map { taskGroups ->
            taskGroups.toDomainTaskGroup()
        }

    suspend fun new(title: String, sessionId: Long) = taskGroupDataSource
        .insert(
            title = title,
            sessionId = sessionId
        )

    suspend fun deleteById(taskGroupId: Long) = taskGroupDataSource
        .deleteById(taskGroupId)

    suspend fun get(taskGroupId: Long) = taskGroupDataSource
        .get(taskGroupId)
        .map { it.toDomainTaskGroup() }

    suspend fun getBySessionId(sessionId: Long) = taskGroupDataSource
        .getBySessionId(sessionId)
        .map { taskGroups ->
            taskGroups.toDomainTaskGroup()
        }

    fun getLastInsertId() = taskGroupDataSource
        .getLastInsertId()

    suspend fun setTitle(taskGroupId: Long, title: String) = taskGroupDataSource
        .setTitle(taskGroupId, title)

    suspend fun setColor(taskGroupId: Long, color: Int) = taskGroupDataSource
        .setColor(taskGroupId, color.toLong())
}

private fun List<DatabaseTaskGroup>.toDomainTaskGroup(): List<DomainTaskGroup> {
    return map { databaseTaskGroup ->
        databaseTaskGroup.toDomainTaskGroup()
    }
}

private fun DatabaseTaskGroup.toDomainTaskGroup(): DomainTaskGroup {
    val title = this.title ?: ""
    val color = this.color?.toInt() ?: 0xFF0000

    return DomainTaskGroup(
        this.id,
        title,
        color,
        emptyList(),
        this.sessionId
    )
}
