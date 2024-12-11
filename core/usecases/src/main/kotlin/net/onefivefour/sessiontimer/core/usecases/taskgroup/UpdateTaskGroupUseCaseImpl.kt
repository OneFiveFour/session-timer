package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.usecases.api.taskgroup.UpdateTaskGroupUseCase

@ViewModelScoped
class UpdateTaskGroupUseCaseImpl @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) : UpdateTaskGroupUseCase {

    override suspend fun execute(
        id: Long,
        title: String,
        color: Int,
        playMode: PlayMode,
        numberOfRandomTasks: Int
    ) {
        taskGroupRepository.updateTaskGroup(
            id,
            title,
            color,
            playMode,
            numberOfRandomTasks
        )
    }
}
