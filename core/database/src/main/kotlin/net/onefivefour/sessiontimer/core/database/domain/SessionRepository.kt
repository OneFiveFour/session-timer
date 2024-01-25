package net.onefivefour.sessiontimer.core.database.domain

import androidx.core.graphics.toColorInt
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.database.FullSession
import net.onefivefour.sessiontimer.core.database.data.SessionDataSource
import net.onefivefour.sessiontimer.core.database.domain.model.Task
import net.onefivefour.sessiontimer.core.database.domain.model.TaskGroup
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.core.database.Session as DatabaseSession
import net.onefivefour.sessiontimer.core.database.domain.model.Session as DomainSession

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

    suspend fun new() = sessionDataSource
        .insert()
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
                val taskGroupColor = fullSession.taskGroupColor ?: 0xFFFF00

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
                    taskGroupColor.toColorInt(),
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
