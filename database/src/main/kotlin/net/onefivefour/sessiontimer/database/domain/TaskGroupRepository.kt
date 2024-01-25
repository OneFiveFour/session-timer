package net.onefivefour.sessiontimer.database.domain

import androidx.core.graphics.toColorInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.database.data.TaskGroupDataSource
import net.onefivefour.sessiontimer.database.domain.model.TaskGroup
import javax.inject.Inject
import net.onefivefour.sessiontimer.database.TaskGroup as DatabaseTaskGroup
import net.onefivefour.sessiontimer.database.domain.model.TaskGroup as DomainTaskGroup

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
    val color = this.color ?: 0xFF0000

    return DomainTaskGroup(
        this.id,
        title,
        color.toColorInt(),
        emptyList(),
        this.sessionId
    )
}