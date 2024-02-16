package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import javax.inject.Inject

@ViewModelScoped
class SetTaskGroupNumberOfRandomTasksUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) {

    suspend fun execute(taskGroupId: Long, number: Int) {
        taskGroupRepository.setNumberOfRandomTasks(taskGroupId, number)
    }
}