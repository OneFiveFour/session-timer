package net.onefivefour.sessiontimer.sessioneditor

import kotlinx.coroutines.flow.first
import net.onefivefour.sessiontimer.database.domain.SessionRepository
import net.onefivefour.sessiontimer.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.database.domain.TaskRepository
import net.onefivefour.sessiontimer.database.domain.model.Session
import net.onefivefour.sessiontimer.database.domain.model.Task
import net.onefivefour.sessiontimer.database.domain.model.TaskGroup
import javax.inject.Inject

class NewTaskGroupUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
){

    suspend fun execute(sessionId: Long) {
        taskGroupRepository.new(sessionId)
    }

}
