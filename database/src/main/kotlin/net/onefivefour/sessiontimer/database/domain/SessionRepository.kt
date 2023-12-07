package net.onefivefour.sessiontimer.database.domain

import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.database.data.SessionDataSource
import javax.inject.Inject
import net.onefivefour.sessiontimer.database.Session as DatabaseSession
import net.onefivefour.sessiontimer.database.domain.model.Session as DomainSession

class SessionRepository @Inject constructor(
    private val sessionDataSource: SessionDataSource
) {

    fun getAll() = sessionDataSource
        .getAll()
        .map(List<DatabaseSession>::toDomainSession)
}

private fun List<DatabaseSession>.toDomainSession(): List<DomainSession> {
    return map { databaseSession ->
        DomainSession(
            databaseSession.id,
            databaseSession.title,
            emptyList()
        )
    }
}
