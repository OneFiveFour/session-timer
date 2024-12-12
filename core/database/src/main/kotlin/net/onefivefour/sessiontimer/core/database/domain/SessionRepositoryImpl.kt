package net.onefivefour.sessiontimer.core.database.domain

import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session as DomainSession
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.DenormalizedSessionView
import net.onefivefour.sessiontimer.core.database.Session as DatabaseSession
import net.onefivefour.sessiontimer.core.database.data.SessionDataSource

internal class SessionRepositoryImpl @Inject constructor(
    private val sessionDataSource: SessionDataSource
): SessionRepository {

    override suspend fun newSession(title: String) = sessionDataSource
        .insert(title)

    override fun getAllSessions() = sessionDataSource
        .getAll()
        .map { it.toDomainSession() }

    override suspend fun getSession(sessionId: Long) = sessionDataSource
        .getDenormalizedSessionView(sessionId)
        .map { it.toDomainSession() }

    override suspend fun deleteSessionById(sessionId: Long) = sessionDataSource
        .deleteById(sessionId)

    override suspend fun setSessionTitle(sessionId: Long, title: String) = sessionDataSource
        .setTitle(sessionId, title)

    override fun getLastInsertId() = sessionDataSource
        .getLastInsertId()
}

internal fun List<DenormalizedSessionView>.toDomainSession(): DomainSession? {
    val firstSession = this.firstOrNull() ?: return null

    val sessionId = firstSession.sessionId
    val sessionTitle = firstSession.sessionTitle

    val taskGroups = this
        .groupBy { it.taskGroupId }
        .mapNotNull taskGroups@{ (taskGroupId, fullSessions) ->

            if (taskGroupId == null) {
                return@taskGroups null
            }

            // taskGroupId represents the current taskGroup id
            // fullSessions represents all joined database rows of this task group
            //   1 row is basically 1 Task with all the Session/TaskGroup information attached.

            // sanity checks
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
            val tasks = fullSessions.mapNotNull tasks@{ taskRow ->

                val taskId = taskRow.taskId ?: return@tasks null

                // sanity checks
                checkNotNull(taskRow.taskTitle)
                checkNotNull(taskRow.taskDuration)

                Task(
                    id = taskId,
                    title = taskRow.taskTitle,
                    duration = taskRow.taskDuration.seconds,
                    taskGroupId = taskGroupId
                )
            }

            TaskGroup(
                id = taskGroupId,
                title = taskGroupTitle,
                color = taskGroupColor,
                playMode = taskGroupPlayMode,
                tasks = tasks,
                numberOfRandomTasks = taskGroupNumberOfRandomTasks,
                sessionId = sessionId
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

internal fun DatabaseSession.toDomainSession(): DomainSession {
    return DomainSession(
        this.id,
        this.title,
        emptyList()
    )
}
