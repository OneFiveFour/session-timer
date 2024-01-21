package net.onefivefour.sessiontimer.sessioneditor

import net.onefivefour.sessiontimer.database.domain.TaskGroupRepository
import javax.inject.Inject

class NewTaskGroupUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
){

    suspend fun execute(sessionId: Long) {
        taskGroupRepository.new(sessionId)
    }

}
