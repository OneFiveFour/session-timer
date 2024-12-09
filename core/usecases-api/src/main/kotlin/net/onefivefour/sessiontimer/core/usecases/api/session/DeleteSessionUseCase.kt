package net.onefivefour.sessiontimer.core.usecases.api.session

interface DeleteSessionUseCase {
    suspend fun execute(sessionId: Long)
}