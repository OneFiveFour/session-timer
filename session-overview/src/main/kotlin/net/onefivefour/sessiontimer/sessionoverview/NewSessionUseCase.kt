package net.onefivefour.sessiontimer.sessionoverview

import net.onefivefour.sessiontimer.database.domain.SessionRepository
import javax.inject.Inject

internal class NewSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {

    suspend fun execute() {
        sessionRepository.new()
    }

}
