package net.onefivefour.sessiontimer.core.usecases.api.taskgroup

interface DeleteTaskGroupUseCase {
    suspend fun execute(taskGroupId: Long)
}
