package net.onefivefour.sessiontimer.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import net.onefivefour.sessiontimer.Blip
import net.onefivefour.sessiontimer.BlipQueries
import javax.inject.Inject

internal class BlipDataSourceImpl @Inject constructor(
    private val queries: BlipQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BlipDataSource {

    override suspend fun getAll(blopId: Long): Flow<List<Blip>> {
        return withContext(dispatcher) {
            queries.getAll(blopId).asFlow().mapToList(dispatcher)
        }
    }

    override suspend fun insert(blipId: Long?, title: String, blopId: Long) {
        withContext(dispatcher) {
            queries.insert(blipId, title, blopId)
        }
    }

    override suspend fun delete(blipId: Long) {
        withContext(dispatcher) {
            queries.delete(blipId)
        }
    }
}