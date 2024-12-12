package net.onefivefour.sessiontimer.core.database.domain

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.common.domain.model.Session

interface SessionRepository {
    suspend fun newSession(title: String)
    fun getAllSessions(): Flow<List<Session>>
    suspend fun getSession(sessionId: Long): Flow<Session?>
    suspend fun deleteSessionById(sessionId: Long)
    suspend fun setSessionTitle(sessionId: Long, title: String)
    fun getLastInsertId(): Long
}
