package net.onefivefour.sessiontimer.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.core.di.IoDispatcher
import net.onefivefour.sessiontimer.database.GetById
import net.onefivefour.sessiontimer.database.Session
import net.onefivefour.sessiontimer.database.SessionQueries
import javax.inject.Inject

internal class SessionDataSourceImpl @Inject constructor(
    private val queries: SessionQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SessionDataSource {

    override suspend fun getById(sessionId: Long): Flow<List<GetById>> {
        return queries.getById(sessionId).asFlow().mapToList(dispatcher)
    }

    override fun getAll(): Flow<List<Session>> {
        return queries.getAll().asFlow().mapToList(dispatcher)
    }

    override suspend fun deleteById(sessionId: Long) {
        withContext(dispatcher) {
            queries.delete(sessionId)
        }
    }

    override suspend fun insert(sessionId: Long?, title: String) {
        withContext(dispatcher) {
            queries.insert(sessionId, title)
        }
    }
}
