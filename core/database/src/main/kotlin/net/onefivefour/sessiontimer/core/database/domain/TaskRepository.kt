package net.onefivefour.sessiontimer.core.database.domain

import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.database.data.TaskDataSource
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.core.database.Task as DatabaseTask
import net.onefivefour.sessiontimer.core.database.domain.model.Task as DomainTask

class TaskRepository @Inject constructor(
    private val taskDataSource: TaskDataSource
) {

    suspend fun getAll(taskGroupIds: List<Long>) = taskDataSource
        .getAll(taskGroupIds)
        .map { tasks ->
            tasks.toDomainTask()
        }

    suspend fun new(taskGroupId: Long) = taskDataSource
        .insert(
            taskGroupId = taskGroupId
        )

    suspend fun delete(taskId: Long) = taskDataSource
        .delete(taskId)

    suspend fun deleteByTaskGroup(taskGroupId: Long) = taskDataSource
        .deleteByTaskGroup(taskGroupId)

}

private fun List<DatabaseTask>.toDomainTask(): List<DomainTask> {
    return map { databaseTask ->
        databaseTask.toDomainTask()
    }
}

private fun DatabaseTask.toDomainTask(): DomainTask {
    return DomainTask(
        id = this.id,
        title = this.title,
        durationInSeconds = this.durationInSeconds?.seconds,
        taskGroupId = this.taskGroupId
    )
}