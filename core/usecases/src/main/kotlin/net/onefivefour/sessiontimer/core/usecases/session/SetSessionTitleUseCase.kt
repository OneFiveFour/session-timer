package net.onefivefour.sessiontimer.core.usecases.session

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository

@ViewModelScoped
class SetSessionTitleUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {

    suspend fun execute(sessionId: Long, title: String) {
        sessionRepository.setTitle(sessionId, title)
    }
}
