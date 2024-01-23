package net.onefivefour.sessiontimer.database.domain

import androidx.core.graphics.toColorInt
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.database.GetById
import net.onefivefour.sessiontimer.database.data.SessionDataSource
import net.onefivefour.sessiontimer.database.domain.model.Task
import net.onefivefour.sessiontimer.database.domain.model.TaskGroup
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.database.Session as DatabaseSession
import net.onefivefour.sessiontimer.database.domain.model.Session as DomainSession

class SessionRepository @Inject constructor(
    private val sessionDataSource: SessionDataSource
) {

    fun getAll() = sessionDataSource
        .getAll()
        .map(List<DatabaseSession>::toDomainSession)

    suspend fun getById(sessionId: Long) = sessionDataSource
        .getById(sessionId)
        .map { getById ->
            getById.toDomainSession()
        }

}

private fun List<GetById>.toDomainSession(): DomainSession? {

    val sessionId = this.firstOrNull()?.sessionId ?: return null
    val sessionTitle = this.firstOrNull()?.sessionTitle ?: ""

    val taskGroups = this
        .groupBy { it.taskGroupId }
        .mapNotNull taskGroupMap@{ (taskGroupId, getById) ->

            if (taskGroupId == null) return@taskGroupMap  null

            val taskGroupTitle = getById.firstOrNull()?.taskGroupTitle ?: ""
            val taskGroupColor = getById.firstOrNull()?.taskGroupColor ?: 0xFFFF00

            val tasks = getById.mapNotNull taskMap@{ taskRow ->

                if (taskRow.taskId == null) return@taskMap null

                Task(
                    taskRow.taskId,
                    taskRow.taskTitle,
                    taskRow.taskDuration?.seconds,
                    taskGroupId
                )
            }

            TaskGroup(
                taskGroupId,
                taskGroupTitle,
                taskGroupColor.toColorInt(),
                tasks,
                sessionId
            )
        }

    return DomainSession(
        sessionId,
        sessionTitle,
        taskGroups
    )
}

private fun List<DatabaseSession>.toDomainSession(): List<DomainSession> {
    return map { databaseSession ->
        databaseSession.toDomainSession()
    }
}

private fun DatabaseSession.toDomainSession(): DomainSession {
    return DomainSession(
        this.id,
        this.title,
        emptyList()
    )
}
