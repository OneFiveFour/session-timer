package net.onefivefour.sessiontimer.core.usecases.api.session

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.Session

interface GetAllSessionsUseCase {
    fun execute(): Flow<List<Session>>
}
