package net.onefivefour.sessiontimer.core.database.data

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.database.DenormalizedSessionView
import net.onefivefour.sessiontimer.core.database.Session

internal interface SessionDataSource {

    suspend fun insert(title: String)

    fun getAll(): Flow<List<Session>>

    suspend fun getDenormalizedSessionView(sessionId: Long): Flow<List<DenormalizedSessionView>>

    suspend fun deleteById(sessionId: Long)

    suspend fun setTitle(sessionId: Long, title: String)

    fun getLastInsertId(): Long
}
