package net.onefivefour.sessiontimer.core.usecases.session

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.database.domain.SessionRepository
import org.junit.Test


class GetFullSessionUseCaseTest {

    private val sessionRepository: SessionRepository = mockk()

    private val sut = GetFullSessionUseCase(
        sessionRepository
    )

    @Test
    fun `executing the use case returns a session with all its task groups and tasks`() = runTest {
        val sessionId = 1L
        val taskGroupId = 2L

        coEvery { sessionRepository.getFullSession(any()) } returns flowOf(
            Session(
                id = sessionId,
                title = "Session 1",
                taskGroups = listOf(
                    TaskGroup(
                        taskGroupId,
                        "TaskGroup 1",
                        0xFF0000,
                        PlayMode.RANDOM,
                        5,
                        listOf(
                            Task(
                                3L,
                                "Task 1",
                                3.seconds,
                                taskGroupId
                            )
                        ),
                        sessionId
                    )
                )
            )
        )

        val fullSessionFlow = sut.execute(sessionId)
        fullSessionFlow.test {
            val fullSession = awaitItem()
            checkNotNull(fullSession)
            assertThat(fullSession.id).isEqualTo(sessionId)
            assertThat(fullSession.title).isEqualTo("Session 1")
            assertThat(fullSession.taskGroups).hasSize(1)

            val taskGroup = fullSession.taskGroups.first()
            assertThat(taskGroup.id).isEqualTo(taskGroupId)
            assertThat(taskGroup.title).isEqualTo("TaskGroup 1")
            assertThat(taskGroup.color).isEqualTo(0xFF0000)
            assertThat(taskGroup.playMode).isEqualTo(PlayMode.RANDOM)
            assertThat(taskGroup.numberOfRandomTasks).isEqualTo(5)
            assertThat(taskGroup.sessionId).isEqualTo(sessionId)
            assertThat(taskGroup.tasks).hasSize(1)

            val task = taskGroup.tasks.first()
            assertThat(task.id).isEqualTo(3L)
            assertThat(task.title).isEqualTo("Task 1")
            assertThat(task.duration).isEqualTo(3.seconds)
            assertThat(task.taskGroupId).isEqualTo(taskGroup.id)

            awaitComplete()
        }
    }

    @Test
    fun `an empty taskGroup must be part of the full session object`() = runTest {
        val sessionId = 1L

        coEvery { sessionRepository.getFullSession(any()) } returns flowOf(
            Session(
                sessionId,
                "Session 1",
                emptyList()
            )
        )

        sut.execute(sessionId).test {
            val session = awaitItem()
            checkNotNull(session)
            assertThat(session.taskGroups).isEmpty()

            awaitComplete()
        }
    }
}
