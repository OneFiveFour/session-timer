package net.onefivefour.sessiontimer.core.usecases.session

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import javax.inject.Inject

@ViewModelScoped
class GetFullSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
){
    suspend fun execute(sessionId: Long) : Flow<Session?> {
        return sessionRepository.getFullSession(sessionId)
    }
}
