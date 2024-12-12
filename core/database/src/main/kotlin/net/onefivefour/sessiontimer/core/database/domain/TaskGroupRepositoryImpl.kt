package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup as DomainTaskGroup
import net.onefivefour.sessiontimer.core.database.TaskGroup as DatabaseTaskGroup
import net.onefivefour.sessiontimer.core.database.data.TaskGroupDataSource

internal class TaskGroupRepositoryImpl @Inject constructor(
    private val taskGroupDataSource: TaskGroupDataSource
): TaskGroupRepository {

    override suspend fun newTaskGroup(
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

    override suspend fun getTaskGroupById(taskGroupId: Long) = taskGroupDataSource
        .getById(taskGroupId)
        .map { it.toDomainTaskGroup() }

    override suspend fun getTaskGroupBySessionId(sessionId: Long) = taskGroupDataSource
        .getBySessionId(sessionId)
        .map { it.toDomainTaskGroup() }

    override suspend fun updateTaskGroup(
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

    override suspend fun deleteTaskGroupById(taskGroupId: Long) = taskGroupDataSource
        .deleteById(taskGroupId)

    override fun getLastInsertId() = taskGroupDataSource
        .getLastInsertId()
}

private fun List<DatabaseTaskGroup>.toDomainTaskGroup(): List<DomainTaskGroup> {
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
