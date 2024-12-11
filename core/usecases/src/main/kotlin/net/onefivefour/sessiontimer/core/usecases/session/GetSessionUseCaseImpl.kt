package net.onefivefour.sessiontimer.core.usecases.session

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase

@ViewModelScoped
class GetSessionUseCaseImpl @Inject constructor(
    private val sessionRepository: SessionRepository
) : GetSessionUseCase {
    override suspend fun execute(sessionId: Long): Flow<Session?> {
        return sessionRepository.getSession(sessionId)
    }
}
