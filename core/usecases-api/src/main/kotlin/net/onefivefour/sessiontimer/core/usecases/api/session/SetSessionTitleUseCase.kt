package net.onefivefour.sessiontimer.core.usecases.api.session

interface SetSessionTitleUseCase {
    suspend fun execute(sessionId: Long, title: String)
}
