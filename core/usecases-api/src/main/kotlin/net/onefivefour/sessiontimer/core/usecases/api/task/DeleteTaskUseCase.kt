package net.onefivefour.sessiontimer.core.usecases.api.task

interface DeleteTaskUseCase {
    suspend fun execute(taskId: Long)
}
