package net.onefivefour.sessiontimer.core.usecases

import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.defaults.DatabaseDefaultValuesProvider
import javax.inject.Inject

class NewSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val defaultValuesProvider: DatabaseDefaultValuesProvider
) {

    suspend fun execute() {
        sessionRepository.new(defaultValuesProvider.getSessionTitle())
    }

}
