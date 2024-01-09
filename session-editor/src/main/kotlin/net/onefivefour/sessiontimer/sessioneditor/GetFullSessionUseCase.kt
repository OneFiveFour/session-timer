package net.onefivefour.sessiontimer.sessioneditor

import kotlinx.coroutines.flow.first
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

    suspend fun execute(sessionId: Long) : Session? {

        val session = sessionRepository.getById(sessionId) ?: return null

        val taskGroups = taskGroupRepository.getAll(session.id).first()

        val taskGroupIds = taskGroups.map { it.id }
        val tasks = taskRepository.getAll(taskGroupIds).first()

        return mapToFullSession(
            session,
            taskGroups,
            tasks
        )
    }

    private fun mapToFullSession(
        session: Session,
        taskGroups: List<TaskGroup>,
        tasks: List<Task>
    ): Session {
        val taskGroupsMap = tasks.groupBy { it.taskGroupId }
        val fullTaskGroups = taskGroupsMap.map { (id, tasks) ->
            taskGroups.first { it.id == id }.copy(tasks = tasks)
        }
        return session.copy(taskGroups = fullTaskGroups)
    }
}
