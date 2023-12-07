package net.onefivefour.sessiontimer.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.database.Session

interface SessionDataSource {

    suspend fun getById(sessionId: Long): Session?

    fun getAll(): Flow<List<Session>>

    suspend fun deleteById(sessionId: Long)

    suspend fun insert(sessionId: Long?, title: String)
}
