package net.onefivefour.sessiontimer.core.usecases

import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDefaultValuesProvider
import javax.inject.Inject

class SetTaskDurationUseCase @Inject constructor(
    private val taskRepository: TaskRepository
){

    suspend fun execute(durationInSeconds: Long, taskId: Long) {
        taskRepository.setDuration(durationInSeconds, taskId)
    }

}
