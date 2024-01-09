package net.onefivefour.sessiontimer.sessioneditor

import net.onefivefour.sessiontimer.database.domain.SessionRepository
import net.onefivefour.sessiontimer.database.domain.model.Session
import net.onefivefour.sessiontimer.database.domain.model.Task
import net.onefivefour.sessiontimer.database.domain.model.TaskGroup
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class GetFullSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
){

    fun execute(sessionId: Long) : Session {

        return Session(
            1L,
            "TEST TITLE",
            listOf(
                TaskGroup(
                    2L,
                    "TITLE TASK GROUP",
                    0xFF0000,
                    listOf(
                        Task(3L, "TASK TITLE", 3.seconds)
                    )
                )
            )
        )

    }
}
