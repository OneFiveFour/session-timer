package net.onefivefour.sessiontimer.database

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.Blip

interface BlipDataSource {

    suspend fun getAll(blopId: Long): Flow<List<Blip>>

    suspend fun delete(blipId: Long)

    suspend fun insert(blipId: Long?, title: String, blopId: Long)
}
