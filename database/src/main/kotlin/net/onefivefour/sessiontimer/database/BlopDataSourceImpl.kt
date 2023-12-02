package net.onefivefour.sessiontimer.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.Blop
import net.onefivefour.sessiontimer.BlopQueries
import javax.inject.Inject

internal class BlopDataSourceImpl @Inject constructor(
    private val queries: BlopQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BlopDataSource {



    override suspend fun getAll(sessionId: Long): Flow<List<Blop>> {
        return withContext(dispatcher) {
            queries.getAll(sessionId).asFlow().mapToList(dispatcher)
        }
    }

    override suspend fun insert(blopId: Long?, title: String, color: Long, sessionId: Long) {
        withContext(dispatcher) {
            queries.insert(blopId, title, color, sessionId)
        }
    }

    override suspend fun delete(blopId: Long) {
        withContext(dispatcher) {
            queries.delete(blopId)
        }
    }
}