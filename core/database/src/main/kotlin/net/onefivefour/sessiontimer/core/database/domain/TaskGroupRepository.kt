package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup as DomainTaskGroup
import net.onefivefour.sessiontimer.core.database.TaskGroup as DatabaseTaskGroup
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSource

class TaskGroupRepository @Inject constructor(
    private val taskGroupDataSource: TaskGroupDataSource
) {

    suspend fun new(
        title: String,
        color: Long,
        playMode: PlayMode,
        numberOfRandomTasks: Int,
        sessionId: Long
    ) = taskGroupDataSource
        .insert(
            title = title,
            color = color,
            playMode = playMode.toString(),
            numberOfRandomTasks = numberOfRandomTasks.toLong(),
            sessionId = sessionId
        )

    suspend fun getById(taskGroupId: Long) = taskGroupDataSource
        .getById(taskGroupId)
        .map { it.toDomainTaskGroup() }

    suspend fun getBySessionId(sessionId: Long) = taskGroupDataSource
        .getBySessionId(sessionId)
        .map { it.toDomainTaskGroup() }

    suspend fun update(
        taskGroupId: Long,
        title: String,
        color: Int,
        playMode: PlayMode,
        numberOfRandomTasks: Int
    ) = taskGroupDataSource
        .update(
            taskGroupId,
            title,
            color.toLong(),
            playMode.toString(),
            numberOfRandomTasks.toLong()
        )

    suspend fun deleteById(taskGroupId: Long) = taskGroupDataSource
        .deleteById(taskGroupId)

    fun getLastInsertId() = taskGroupDataSource
        .getLastInsertId()
}

internal fun List<DatabaseTaskGroup>.toDomainTaskGroup(): List<DomainTaskGroup> {
    return map { databaseTaskGroup ->
        databaseTaskGroup.toDomainTaskGroup()
    }
}

internal fun DatabaseTaskGroup.toDomainTaskGroup(): DomainTaskGroup {
    val title = this.title
    val color = this.color
    val playMode = PlayMode.valueOf(this.playMode)
    val numberOfRandomTasks = this.numberOfRandomTasks.toInt()

    return DomainTaskGroup(
        id = this.id,
        title = title,
        color = color,
        playMode = playMode,
        tasks = emptyList(),
        numberOfRandomTasks = numberOfRandomTasks,
        sessionId = this.sessionId
    )
}
