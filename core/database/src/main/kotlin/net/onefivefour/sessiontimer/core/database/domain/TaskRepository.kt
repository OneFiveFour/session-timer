package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.common.domain.model.Task as DomainTask
import net.onefivefour.sessiontimer.core.database.Task as DatabaseTask
import net.onefivefour.sessiontimer.core.database.data.TaskDataSource
import kotlin.time.Duration
import kotlin.time.DurationUnit

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

private fun List<DatabaseTask>.toDomainTask(): List<DomainTask> {
    return map { databaseTask ->
        databaseTask.toDomainTask()
    }
}

private fun DatabaseTask.toDomainTask(): DomainTask {
    return DomainTask(
        id = this.id,
        title = this.title,
        duration = this.durationInSeconds.seconds,
        taskGroupId = this.taskGroupId
    )
}
