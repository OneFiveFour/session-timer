package net.onefivefour.sessiontimer.core.usecases

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import javax.inject.Inject

class GetFullSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
){
    suspend fun execute(sessionId: Long) : Flow<Session?> {
        return sessionRepository.getFullSession(sessionId)
    }
}
