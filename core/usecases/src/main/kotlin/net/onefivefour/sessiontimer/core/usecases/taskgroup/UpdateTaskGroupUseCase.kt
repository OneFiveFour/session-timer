package net.onefivefour.sessiontimer.core.usecases.taskgroup

import dagger.hilt.android.scopes.ViewModelScoped
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import javax.inject.Inject

@ViewModelScoped
class UpdateTaskGroupUseCase @Inject constructor(
    private val taskGroupRepository: TaskGroupRepository
) {

    suspend fun execute(
        id: Long,
        title: String,
        color: Int,
        playMode: PlayMode,
        numberOfRandomTasks: Int
    ) {
        taskGroupRepository.update(
            id,
            title,
            color,
            playMode,
            numberOfRandomTasks
        )
    }
}