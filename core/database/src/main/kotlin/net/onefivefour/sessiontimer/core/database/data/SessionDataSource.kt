package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow

interface SessionDataSource {

    suspend fun getFullSession(sessionId: Long): Flow<List<FullSession>>

    fun getAll(): Flow<List<Session>>

    suspend fun deleteById(sessionId: Long)

    suspend fun insert(defaultTitle: String)
}
