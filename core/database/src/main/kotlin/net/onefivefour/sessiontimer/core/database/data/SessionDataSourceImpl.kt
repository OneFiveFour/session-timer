package net.onefivefour.sessiontimer.core.database.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.core.database.SessionQueries
import net.onefivefour.sessiontimer.core.di.IoDispatcher

internal class SessionDataSourceImpl @Inject constructor(
    private val queries: SessionQueries,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SessionDataSource {

    override suspend fun insert(title: String) {
        withContext(dispatcher) {
            queries.new(null, title)
        }
    }

    override fun getAll() = queries
        .getAll()
        .asFlow()
        .mapToList(dispatcher)

    override suspend fun getFullSessionById(sessionId: Long) = queries
        .getFullSessionById(sessionId)
        .asFlow()
        .mapToList(dispatcher)

    override suspend fun deleteById(sessionId: Long) {
        withContext(dispatcher) {
            queries.deleteById(sessionId)
        }
    }

    override suspend fun setTitle(sessionId: Long, title: String) {
        withContext(dispatcher) {
            queries.setTitle(sessionId = sessionId, title = title)
        }
    }

    override fun getLastInsertId() = queries
        .getLastInsertRowId()
        .executeAsOne()
}
