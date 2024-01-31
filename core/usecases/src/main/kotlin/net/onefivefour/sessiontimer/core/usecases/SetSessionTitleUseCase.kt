package net.onefivefour.sessiontimer.core.usecases

import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import javax.inject.Inject

class SetSessionTitleUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
){

    suspend fun execute(sessionId: Long, title: String) {
        sessionRepository.setTitle(sessionId, title)
    }


}
