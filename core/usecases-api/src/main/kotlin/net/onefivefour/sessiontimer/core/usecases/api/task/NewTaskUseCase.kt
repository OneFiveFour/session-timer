package net.onefivefour.sessiontimer.core.usecases.api.task

interface NewTaskUseCase {
    suspend fun execute(taskGroupId: Long)
}