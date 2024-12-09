package net.onefivefour.sessiontimer.core.usecases.api.session

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.Session

interface GetSessionUseCase {
    suspend fun execute(sessionId: Long): Flow<Session?>
}