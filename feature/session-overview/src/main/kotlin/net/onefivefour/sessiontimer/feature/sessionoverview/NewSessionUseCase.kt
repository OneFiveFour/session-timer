package net.onefivefour.sessiontimer.feature.sessionoverview

import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import javax.inject.Inject

internal class NewSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {

    suspend fun execute() {
        sessionRepository.new()
    }

}
