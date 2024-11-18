package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session as DomainSession
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.Session as DatabaseSession
import net.onefivefour.sessiontimer.core.database.data.FullSession
import net.onefivefour.sessiontimer.core.database.data.SessionDataSource

class SessionRepository @Inject constructor(
    private val sessionDataSource: SessionDataSource
) {

    suspend fun new(title: String) = sessionDataSource
        .insert(title)

    fun getAll() = sessionDataSource
        .getAll()
        .map { it.toDomainSession() }

    suspend fun getFullSession(sessionId: Long) = sessionDataSource
        .getFullSessionById(sessionId)
        .map { it.toDomainSession() }

    suspend fun deleteById(sessionId: Long) = sessionDataSource
        .deleteById(sessionId)

    suspend fun setTitle(sessionId: Long, title: String) = sessionDataSource
        .setTitle(sessionId, title)

    fun getLastInsertId() = sessionDataSource
        .getLastInsertId()
}

internal fun List<FullSession>.toDomainSession(): DomainSession? {
    val firstSession = this.firstOrNull() ?: return null

    val sessionId = firstSession.sessionId
    val sessionTitle = firstSession.sessionTitle

    val taskGroups = this
        .groupBy { it.taskGroupId }
        .mapNotNull { (taskGroupId, fullSessions) ->

            // taskGroupId represents the current taskGroup id
            // fullSessions represents all joined database rows of this task group
            //   1 row is basically 1 Task with all the Session/TaskGroup information attached.

            // sanity checks
            checkNotNull(taskGroupId)
            check(fullSessions.isNotEmpty())

            // extract task group data
            val fullSession = fullSessions.first()

            // sanity checks
            checkNotNull(fullSession.taskGroupTitle)
            checkNotNull(fullSession.taskGroupColor)
            checkNotNull(fullSession.taskGroupPlayMode)
            checkNotNull(fullSession.taskGroupNumberOfRandomTasks)

            val taskGroupTitle = fullSession.taskGroupTitle
            val taskGroupColor = fullSession.taskGroupColor
            val taskGroupPlayMode = PlayMode.valueOf(fullSession.taskGroupPlayMode)
            val taskGroupNumberOfRandomTasks = fullSession.taskGroupNumberOfRandomTasks.toInt()

            // extract tasks
            val tasks = fullSessions.map { taskRow ->

                // sanity checks
                checkNotNull(taskRow.taskId)
                checkNotNull(taskRow.taskTitle)
                checkNotNull(taskRow.taskDuration)

                Task(
                    id = taskRow.taskId,
                    title = taskRow.taskTitle,
                    duration = taskRow.taskDuration.seconds,
                    taskGroupId = taskGroupId
                )
            }

            TaskGroup(
                taskGroupId,
                taskGroupTitle,
                taskGroupColor,
                taskGroupPlayMode,
                taskGroupNumberOfRandomTasks,
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

internal fun List<DatabaseSession>.toDomainSession(): List<DomainSession> {
    return map { databaseSession ->
        databaseSession.toDomainSession()
    }
}

internal fun DatabaseSession.toDomainSession(): DomainSession {
    return DomainSession(
        this.id,
        this.title,
        emptyList()
    )
}
