package net.onefivefour.sessiontimer.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.Database
import net.onefivefour.sessiontimer.Session
import net.onefivefour.sessiontimer.SessionQueries
import javax.inject.Inject

class SessionDataSourceImpl @Inject constructor(
    database: Database
) : SessionDataSource {

    private val queries : SessionQueries = database.sessionQueries

    override suspend fun getById(sessionId: Long): Session? {
        return withContext(Dispatchers.IO) {
            queries.getById(sessionId).executeAsOneOrNull()
        }
    }

    override fun getAll(): Flow<List<Session>> {
        return queries.getAll().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun deleteById(sessionId: Long) {
        withContext(Dispatchers.IO) {
            queries.deleteById(sessionId)
        }
    }

    override suspend fun insert(sessionId: Long?, title: String) {
        withContext(Dispatchers.IO) {
            queries.insert(sessionId, title)
        }
    }
}