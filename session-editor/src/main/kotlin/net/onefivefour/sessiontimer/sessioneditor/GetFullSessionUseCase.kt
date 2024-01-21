package net.onefivefour.sessiontimer.sessioneditor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.database.domain.SessionRepository
import net.onefivefour.sessiontimer.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.database.domain.TaskRepository
import net.onefivefour.sessiontimer.database.domain.model.Session
import net.onefivefour.sessiontimer.database.domain.model.Task
import net.onefivefour.sessiontimer.database.domain.model.TaskGroup
import javax.inject.Inject

class GetFullSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val taskGroupRepository: TaskGroupRepository,
    private val taskRepository: TaskRepository
){

    suspend fun execute(sessionId: Long) : Flow<Session?> {

        val sessionFlow = sessionRepository.getById(sessionId)

        val taskGroupsFlow = taskGroupRepository.getAll(sessionId)

        val taskGroupIds = taskGroupsFlow.map { taskGroups ->
            taskGroups.map { taskGroup -> taskGroup.id }
        }.first()

        val tasksFlow = taskRepository.getAll(taskGroupIds)

        return combine(
            sessionFlow,
            taskGroupsFlow,
            tasksFlow
        ) { session, taskGroups, tasks ->

            if (session == null) {
                return@combine null
            }

            mapToFullSession(
                session, taskGroups, tasks
            )
        }
    }

    private fun mapToFullSession(
        session: Session,
        taskGroups: List<TaskGroup>,
        tasks: List<Task>
    ): Session {

        val taskGroupsToTasks = tasks.groupBy { it.taskGroupId }
        val fullTaskGroups = taskGroups.map { taskGroup ->
            val tasksOfTaskGroup = taskGroupsToTasks[taskGroup.id] ?: emptyList()
            taskGroup.copy(tasks = tasksOfTaskGroup)
        }

        return session.copy(taskGroups = fullTaskGroups)
    }
}
