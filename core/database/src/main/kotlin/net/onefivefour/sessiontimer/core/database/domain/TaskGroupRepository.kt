package net.onefivefour.sessiontimer.core.database.domain

import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSource
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.TaskGroup as DatabaseTaskGroup
import net.onefivefour.sessiontimer.core.database.domain.model.TaskGroup as DomainTaskGroup

class TaskGroupRepository @Inject constructor(
    private val taskGroupDataSource: TaskGroupDataSource
) {

    suspend fun getAll(sessionId: Long) = taskGroupDataSource
        .getAll(sessionId)
        .map { taskGroups ->
            taskGroups.toDomainTaskGroup()
        }

    suspend fun new(sessionId: Long) = taskGroupDataSource
        .insert(
            sessionId = sessionId
        )

    suspend fun delete(taskGroupId: Long) = taskGroupDataSource
        .delete(taskGroupId)

    suspend fun get(taskGroupId: Long) = taskGroupDataSource
        .get(taskGroupId)
        .map { it.toDomainTaskGroup() }

    fun getLastInsertId() = taskGroupDataSource
        .getLastInsertId()


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