package net.onefivefour.sessiontimer.feature.sessioneditor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.core.database.domain.TaskRepository
import net.onefivefour.sessiontimer.core.database.domain.model.Session
import net.onefivefour.sessiontimer.core.database.domain.model.Task
import net.onefivefour.sessiontimer.core.database.domain.model.TaskGroup
import javax.inject.Inject

internal class GetFullSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
){
    suspend fun execute(sessionId: Long) : Flow<Session?> {
        return sessionRepository.getFullSession(sessionId)
    }
}