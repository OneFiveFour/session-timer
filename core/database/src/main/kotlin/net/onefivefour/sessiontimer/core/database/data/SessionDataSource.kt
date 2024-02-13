package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.Session

interface SessionDataSource {

    suspend fun insert(title: String)

    fun getAll(): Flow<List<Session>>

    suspend fun getFullSession(sessionId: Long): Flow<List<FullSession>>

    suspend fun setTitle(sessionId: Long, title: String)

    suspend fun deleteById(sessionId: Long)

    fun getLastInsertId(): Long
}
