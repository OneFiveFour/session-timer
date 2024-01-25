package net.onefivefour.sessiontimer.sessionoverview

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.database.domain.SessionRepository
import net.onefivefour.sessiontimer.database.domain.model.Session
import javax.inject.Inject

class GetAllSessionsUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
){

    fun execute(): Flow<List<Session>> {
        return sessionRepository.getAll()
    }

}
