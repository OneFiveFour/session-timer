package net.onefivefour.sessiontimer.core.usecases.session

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.usecases.api.session.GetAllSessionsUseCase

@ViewModelScoped
class GetAllSessionsUseCaseImpl @Inject constructor(
    private val sessionRepository: SessionRepository
) : GetAllSessionsUseCase {

    override fun execute(): Flow<List<Session>> {
        return sessionRepository.getAllSessions()
    }
}
