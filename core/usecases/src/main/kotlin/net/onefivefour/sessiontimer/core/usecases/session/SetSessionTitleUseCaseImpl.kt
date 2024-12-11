package net.onefivefour.sessiontimer.core.usecases.session

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.usecases.api.session.SetSessionTitleUseCase

@ViewModelScoped
class SetSessionTitleUseCaseImpl @Inject constructor(
    private val sessionRepository: SessionRepository
) : SetSessionTitleUseCase {

    override suspend fun execute(sessionId: Long, title: String) {
        sessionRepository.setSessionTitle(sessionId, title)
    }
}
