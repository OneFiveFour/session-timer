package net.onefivefour.sessiontimer.core.database.domain

import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.data.FullSession
import net.onefivefour.sessiontimer.core.database.data.SessionDataSource
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.core.common.domain.model.Session as DomainSession
import net.onefivefour.sessiontimer.core.database.Session as DatabaseSession

class SessionRepository @Inject constructor(
    private val sessionDataSource: SessionDataSource
) {

    suspend fun new(title: String) = sessionDataSource
        .insert(title)

    fun getAll() = sessionDataSource
        .getAll()
        .map { it.toDomainSession() }

    suspend fun getFullSession(sessionId: Long) = sessionDataSource
        .getFullSession(sessionId)
        .map { it.toDomainSession() }

    suspend fun deleteById(sessionId: Long) = sessionDataSource
        .deleteById(sessionId)

    suspend fun setTitle(sessionId: Long, title: String) = sessionDataSource
        .setTitle(sessionId, title)

    fun getLastInsertId() = sessionDataSource
        .getLastInsertId()
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
                            duration = taskRow.taskDuration?.seconds,
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
