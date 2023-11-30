package net.onefivefour.sessiontimer.database

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.Blop

interface BlopDataSource {

    suspend fun getAll(sessionId: Long): Flow<List<Blop>>

    suspend fun delete(blopId: Long)

    suspend fun insert(blopId: Long?, title: String, color: Long, sessionId: Long)
}
