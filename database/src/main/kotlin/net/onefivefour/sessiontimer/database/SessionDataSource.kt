package net.onefivefour.sessiontimer.database

import kotlinx.coroutines.flow.Flow

interface SessionDataSource {

    suspend fun getById(sessionId: Long): Session?

    fun getAll(): Flow<List<Session>>

    suspend fun deleteById(sessionId: Long)

    suspend fun insert(sessionId: Long?, title: String)
}
