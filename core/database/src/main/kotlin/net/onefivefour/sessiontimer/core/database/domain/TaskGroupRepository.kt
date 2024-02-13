package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.database.TaskGroup
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup as DomainTaskGroup
import net.onefivefour.sessiontimer.core.database.TaskGroup as DatabaseTaskGroup
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSource

class TaskGroupRepository @Inject constructor(
    private val taskGroupDataSource: TaskGroupDataSource
) {

    suspend fun new(title: String, color: Int, sessionId: Long) = taskGroupDataSource
        .insert(
            title = title,
            color = color.toLong(),
            sessionId = sessionId
        )

    suspend fun getById(taskGroupId: Long) = taskGroupDataSource
        .getById(taskGroupId)
        .map { it.toDomainTaskGroup() }

    suspend fun getBySessionId(sessionId: Long) = taskGroupDataSource
        .getBySessionId(sessionId)
        .map { it.toDomainTaskGroup() }

    suspend fun deleteById(taskGroupId: Long) = taskGroupDataSource
        .deleteById(taskGroupId)

    suspend fun setTitle(taskGroupId: Long, title: String) = taskGroupDataSource
        .setTitle(taskGroupId, title)

    suspend fun setColor(taskGroupId: Long, color: Int) = taskGroupDataSource
        .setColor(taskGroupId, color.toLong())

    fun getLastInsertId() = taskGroupDataSource
        .getLastInsertId()
}

private fun List<DatabaseTaskGroup>.toDomainTaskGroup(): List<DomainTaskGroup> {
    return map { databaseTaskGroup ->
        databaseTaskGroup.toDomainTaskGroup()
    }
}

private fun DatabaseTaskGroup.toDomainTaskGroup(): DomainTaskGroup {
    val title = this.title
    val color = this.color.toInt()

    return DomainTaskGroup(
        this.id,
        title,
        color,
        emptyList(),
        this.sessionId
    )
}
