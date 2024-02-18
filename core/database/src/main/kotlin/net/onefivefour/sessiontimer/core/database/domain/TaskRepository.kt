package net.onefivefour.sessiontimer.core.database.domain

import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.database.data.TaskDataSource
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import net.onefivefour.sessiontimer.core.common.domain.model.Task as DomainTask
import net.onefivefour.sessiontimer.core.database.Task as DatabaseTask

class TaskRepository @Inject constructor(
    private val taskDataSource: TaskDataSource
) {

    suspend fun new(title: String, durationInSeconds: Int, taskGroupId: Long) = taskDataSource
        .insert(
            title = title,
            durationInSeconds = durationInSeconds.toLong(),
            taskGroupId = taskGroupId
        )

    suspend fun getByTaskGroupIds(taskGroupIds: List<Long>) = taskDataSource
        .getByTaskGroupIds(taskGroupIds)
        .map { it.toDomainTask() }

    suspend fun update(taskId: Long, title: String, duration: Duration) = taskDataSource
        .update(taskId, title, duration.toLong(DurationUnit.SECONDS))

    suspend fun delete(taskId: Long) = taskDataSource
        .deleteById(taskId)

    suspend fun deleteByTaskGroupId(taskGroupId: Long) = taskDataSource
        .deleteByTaskGroupId(taskGroupId)

}

internal fun List<DatabaseTask>.toDomainTask(): List<DomainTask> {
    return map { databaseTask ->
        databaseTask.toDomainTask()
    }
}

internal fun DatabaseTask.toDomainTask(): DomainTask {
    return DomainTask(
        id = this.id,
        title = this.title,
        duration = this.durationInSeconds.seconds,
        taskGroupId = this.taskGroupId
    )
}
