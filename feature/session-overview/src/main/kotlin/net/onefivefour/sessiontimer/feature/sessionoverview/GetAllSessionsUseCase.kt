package net.onefivefour.sessiontimer.feature.sessionoverview

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.model.Session
import javax.inject.Inject

class GetAllSessionsUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
){

    fun execute(): Flow<List<Session>> {
        return sessionRepository.getAll()
    }

}
