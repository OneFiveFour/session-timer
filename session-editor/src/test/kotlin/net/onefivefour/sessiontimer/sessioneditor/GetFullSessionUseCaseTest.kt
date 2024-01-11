package net.onefivefour.sessiontimer.sessioneditor

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import net.onefivefour.sessiontimer.database.domain.SessionRepository
import net.onefivefour.sessiontimer.database.domain.TaskGroupRepository
import net.onefivefour.sessiontimer.database.domain.TaskRepository
import net.onefivefour.sessiontimer.database.domain.model.Session
import net.onefivefour.sessiontimer.database.domain.model.TaskGroup
import org.junit.jupiter.api.Test


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
    fun `test something`() {
        coEvery { sessionRepositoryMock.getById(any()) } returns Session(
            1L,
            "Session 1",
            emptyList()
        )
        coEvery { taskGroupRepositoryMock.getAll(any()) } returns flowOf(
            listOf(
                TaskGroup(1L, "TaskGroup 1", 0xFF0000, emptyList(), 1L)
            )
        )
    }


}