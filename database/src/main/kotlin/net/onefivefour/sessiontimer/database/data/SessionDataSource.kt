package net.onefivefour.sessiontimer.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.database.FullSession
import net.onefivefour.sessiontimer.database.Session

interface SessionDataSource {

    suspend fun getFullSession(sessionId: Long): Flow<List<FullSession>>

    fun getAll(): Flow<List<Session>>

    suspend fun deleteById(sessionId: Long)

    suspend fun insert()
}
