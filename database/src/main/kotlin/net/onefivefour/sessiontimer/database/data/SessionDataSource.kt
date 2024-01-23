package net.onefivefour.sessiontimer.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.database.GetById
import net.onefivefour.sessiontimer.database.Session

interface SessionDataSource {

    suspend fun getById(sessionId: Long): Flow<List<GetById>>

    fun getAll(): Flow<List<Session>>

    suspend fun deleteById(sessionId: Long)

    suspend fun insert(sessionId: Long?, title: String)
}
