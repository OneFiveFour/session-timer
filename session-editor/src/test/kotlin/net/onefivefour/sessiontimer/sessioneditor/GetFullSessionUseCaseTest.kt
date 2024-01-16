package net.onefivefour.sessiontimer.sessioneditor

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.onefivefour.sessiontimer.database.domain.SessionRepository
import net.onefivefour.sessiontimer.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.database.domain.TaskRepository
import net.onefivefour.sessiontimer.database.domain.model.Session
import net.onefivefour.sessiontimer.database.domain.model.Task
import net.onefivefour.sessiontimer.database.domain.model.TaskGroup
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds


class GetFullSessionUseCaseTest {

    private val sessionRepositoryMock: SessionRepository = mockk()
    private val taskGroupRepositoryMock: TaskGroupRepository = mockk()
    private val taskRepositoryMock: TaskRepository = mockk()

    private val sut = GetFullSessionUseCase(
        sessionRepositoryMock,
        taskGroupRepositoryMock,
        taskRepositoryMock
    )

    @Test
    fun `executing the use case returns a session with all its task groups and tasks`() = runTest {

        val sessionId = 1L
        val taskGroupId = 2L

        coEvery { sessionRepositoryMock.getById(any()) } returns Session(
            sessionId,
            "Session 1",
            emptyList()
        )
        coEvery { taskGroupRepositoryMock.getAll(any()) } returns flowOf(
            listOf(
                TaskGroup(taskGroupId, "TaskGroup 1", 0xFF0000, emptyList(), sessionId)
            )
        )
        coEvery { taskRepositoryMock.getAll(any()) } returns flowOf(
            listOf(
                Task(3L, "Task 1", 3.seconds, taskGroupId)
            )
        )

        val fullSession = sut.execute(sessionId)
        checkNotNull(fullSession)
        assertThat(fullSession.id).isEqualTo(sessionId)
        assertThat(fullSession.title).isEqualTo("Session 1")
        assertThat(fullSession.taskGroups).hasSize(1)

        val taskGroup = fullSession.taskGroups.first()
        assertThat(taskGroup.id).isEqualTo(taskGroupId)
        assertThat(taskGroup.title).isEqualTo("TaskGroup 1")
        assertThat(taskGroup.color).isEqualTo(0xFF0000)
        assertThat(taskGroup.sessionId).isEqualTo(sessionId)
        assertThat(taskGroup.tasks).hasSize(1)

        val task = taskGroup.tasks.first()
        assertThat(task.id).isEqualTo(3L)
        assertThat(task.title).isEqualTo("Task 1")
        assertThat(task.durationInSeconds).isEqualTo(3.seconds)
        assertThat(task.taskGroupId).isEqualTo(taskGroup.id)

    }


}