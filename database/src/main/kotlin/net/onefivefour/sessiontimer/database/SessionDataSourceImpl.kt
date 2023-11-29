package net.onefivefour.sessiontimer.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.Session
import net.onefivefour.sessiontimer.SessionQueries

class SessionDataSourceImpl @Inject constructor(
    private val queries: SessionQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SessionDataSource {

    override suspend fun getById(sessionId: Long): Session? {
        return withContext(dispatcher) {
            queries.getById(sessionId).executeAsOneOrNull()
        }
    }

    override fun getAll(): Flow<List<Session>> {
        return queries.getAll().asFlow().mapToList(dispatcher)
    }

    override suspend fun deleteById(sessionId: Long) {
        withContext(dispatcher) {
            queries.deleteById(sessionId)
        }
    }

    override suspend fun insert(sessionId: Long?, title: String) {
        withContext(dispatcher) {
            queries.insert(sessionId, title)
        }
    }
}
