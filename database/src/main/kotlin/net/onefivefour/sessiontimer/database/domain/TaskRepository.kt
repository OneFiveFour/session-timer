package net.onefivefour.sessiontimer.database.domain

import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.database.data.TaskDataSource
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.database.Task as DatabaseTask
import net.onefivefour.sessiontimer.database.domain.model.Task as DomainTask

class TaskRepository @Inject constructor(
    private val taskDataSource: TaskDataSource
) {

    suspend fun getAll(taskGroupIds: List<Long>) = taskDataSource
        .getAll(taskGroupIds)
        .map { tasks ->
            tasks.toDomainTask()
        }

}

private fun List<DatabaseTask>.toDomainTask(): List<DomainTask> {
    return map { databaseTask ->
        databaseTask.toDomainTask()
    }
}

private fun DatabaseTask.toDomainTask(): DomainTask {
    return DomainTask(
        this.id,
        this.title,
        this.durationInSeconds.seconds,
        this.taskGroupId
    )
}