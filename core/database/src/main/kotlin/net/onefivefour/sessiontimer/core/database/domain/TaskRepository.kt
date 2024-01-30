package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.database.Task as DatabaseTask
import net.onefivefour.sessiontimer.core.database.data.TaskDataSource
import net.onefivefour.sessiontimer.core.common.domain.model.Task as DomainTask

class TaskRepository @Inject constructor(
    private val taskDataSource: TaskDataSource
) {

    suspend fun getAll(taskGroupIds: List<Long>) = taskDataSource
        .getAll(taskGroupIds)
        .map { tasks ->
            tasks.toDomainTask()
        }

    suspend fun new(title: String, taskGroupId: Long) = taskDataSource
        .insert(
            title = title,
            taskGroupId = taskGroupId
        )

    suspend fun delete(taskId: Long) = taskDataSource
        .deleteById(taskId)

    suspend fun deleteByTaskGroup(taskGroupId: Long) = taskDataSource
        .deleteByTaskGroup(taskGroupId)

    suspend fun deleteByIds(taskIds: List<Long>) = taskDataSource
        .deleteByIds(taskIds)

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
