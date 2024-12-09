package net.onefivefour.sessiontimer.core.usecases.api.taskgroup

interface NewTaskGroupUseCase {
    suspend fun execute(sessionId: Long)
}