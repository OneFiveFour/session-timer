package net.onefivefour.sessiontimer.core.usecases.task

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.data.DatabaseDefaultValues
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository

@ViewModelScoped
class NewTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val defaultValues: DatabaseDefaultValues
) {

    suspend fun execute(taskGroupId: Long) {
        val title = defaultValues.getTaskTitle()
        val duration = defaultValues.getTaskDuration()

        taskRepository.new(title, duration, taskGroupId)
    }
}
