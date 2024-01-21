package net.onefivefour.sessiontimer.database.domain

import androidx.core.graphics.toColorInt
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.database.data.TaskGroupDataSource
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

    suspend fun new(sessionId: Long) = taskGroupDataSource.insert(
        taskGroupId = null,
        sessionId = sessionId
    )

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