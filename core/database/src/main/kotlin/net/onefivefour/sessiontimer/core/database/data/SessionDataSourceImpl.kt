package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.core.database.FullSession
import net.onefivefour.sessiontimer.core.database.Session
import net.onefivefour.sessiontimer.core.database.SessionQueries
import net.onefivefour.sessiontimer.core.di.IoDispatcher
import javax.inject.Inject

internal class SessionDataSourceImpl @Inject constructor(
    private val queries: SessionQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SessionDataSource {

    override suspend fun getFullSession(sessionId: Long): Flow<List<FullSession>> {
        return queries.fullSession(sessionId).asFlow().mapToList(dispatcher)
    }

    override fun getAll(): Flow<List<Session>> {
        return queries.getAll().asFlow().mapToList(dispatcher)
    }

    override suspend fun deleteById(sessionId: Long) {
        withContext(dispatcher) {
            queries.delete(sessionId)
        }
    }

    override suspend fun insert() {
        withContext(dispatcher) {
            queries.insert(null, null)
        }
    }
}