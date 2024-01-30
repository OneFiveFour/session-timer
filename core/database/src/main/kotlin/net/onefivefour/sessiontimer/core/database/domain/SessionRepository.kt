package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.database.FullSession
import net.onefivefour.sessiontimer.core.database.Session as DatabaseSession
import net.onefivefour.sessiontimer.core.database.data.SessionDataSource
import net.onefivefour.sessiontimer.core.common.domain.model.Session as DomainSession
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup

class SessionRepository @Inject constructor(
    private val sessionDataSource: SessionDataSource
) {

    fun getAll() = sessionDataSource
        .getAll()
        .map(List<DatabaseSession>::toDomainSession)

    suspend fun getFullSession(sessionId: Long) = sessionDataSource
        .getFullSession(sessionId)
        .map { fullSession ->
            fullSession.toDomainSession()
        }

    suspend fun new(defaultTitle: String) = sessionDataSource
        .insert(defaultTitle)
}

private fun List<FullSession>.toDomainSession(): DomainSession? {
    val firstSession = this.firstOrNull() ?: return null

    val sessionId = firstSession.sessionId
    val sessionTitle = firstSession.sessionTitle ?: ""

    val taskGroups = this
        .groupBy { it.taskGroupId }
        .mapNotNull { (taskGroupId, fullSessions) ->

            taskGroupId?.let {
                val fullSession = fullSessions.firstOrNull() ?: return null
                val taskGroupTitle = fullSession.taskGroupTitle ?: ""
                val taskGroupColor = fullSession.taskGroupColor?.toInt() ?: 0xFFFF00

                val tasks = fullSessions.mapNotNull { taskRow ->

                    taskRow.taskId?.let {
                        Task(
                            id = taskRow.taskId,
                            title = taskRow.taskTitle,
                            durationInSeconds = taskRow.taskDuration?.seconds,
                            taskGroupId = taskGroupId
                        )
                    }
                }

                TaskGroup(
                    taskGroupId,
                    taskGroupTitle,
                    taskGroupColor,
                    tasks,
                    sessionId
                )
            }
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
        this.title ?: "",
        emptyList()
    )
}
