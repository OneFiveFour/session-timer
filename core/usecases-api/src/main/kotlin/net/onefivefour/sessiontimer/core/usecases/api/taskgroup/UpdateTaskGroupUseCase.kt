package net.onefivefour.sessiontimer.core.usecases.api.taskgroup

import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode

interface UpdateTaskGroupUseCase {
    suspend fun execute(
        id: Long,
        title: String,
        color: Int,
        playMode: PlayMode,
        numberOfRandomTasks: Int
    )
}