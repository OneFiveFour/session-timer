package net.onefivefour.sessiontimer.feature.sessionplayer.domain

import androidx.compose.ui.graphics.Color
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import org.junit.Test

internal class SessionCompilerTest {

    @Test
    fun `GIVEN a taskGroup in SEQUENCE play mode WHEN compiled THEN the compiled task sequence is correct`() {
        // GIVEN
        val task1 = mockk<Task> {
            every { id } returns 1L
            every { title } returns "Task 1"
            every { duration } returns 10.seconds
        }
        val task2 = mockk<Task> {
            every { id } returns 2L
            every { title } returns "Task 2"
            every { duration } returns 20.seconds
        }

        val taskGroup = mockk<TaskGroup> {
            every { title } returns "Test Group"
            every { color } returns 0xFFFF0000
            every { playMode } returns PlayMode.SEQUENCE
            every { tasks } returns listOf(task1, task2)
        }

        val session = mockk<Session> {
            every { title } returns "Test Session"
            every { taskGroups } returns listOf(taskGroup)
        }

        // WHEN
        val compiledSession = SessionCompiler.compile(session)

        // THEN
        assertThat(compiledSession.sessionTitle).isEqualTo("Test Session")
        assertThat(compiledSession.taskList.size).isEqualTo(2)
        assertThat(compiledSession.totalDuration).isEqualTo(30.seconds)

        with(compiledSession.taskList[0]) {
            assertThat(taskGroupTitle).isEqualTo("Test Group")
            assertThat(taskGroupColor).isEqualTo(Color(0xFFFF0000))
            assertThat(taskTitle).isEqualTo("Task 1")
            assertThat(taskDuration).isEqualTo(10.seconds)
        }

        with(compiledSession.taskList[1]) {
            assertThat(taskGroupTitle).isEqualTo("Test Group")
            assertThat(taskGroupColor).isEqualTo(Color(0xFFFF0000))
            assertThat(taskTitle).isEqualTo("Task 2")
            assertThat(taskDuration).isEqualTo(20.seconds)
        }
    }

    @Test
    fun `GIVEN a taskGroup in RANDOM_SINGLE_TASK play mode WHEN compiled THEN the compiled task sequence is correct`() {
        // GIVEN
        val task1 = mockk<Task> {
            every { id } returns 1L
            every { title } returns "Task 1"
            every { duration } returns 10.seconds
        }
        val task2 = mockk<Task> {
            every { id } returns 2L
            every { title } returns "Task 2"
            every { duration } returns 20.seconds
        }
        val task3 = mockk<Task> {
            every { id } returns 3L
            every { title } returns "Task 3"
            every { duration } returns 30.seconds
        }

        val taskGroup = mockk<TaskGroup> {
            every { title } returns "Test Group"
            every { color } returns 0xFFFF0000
            every { playMode } returns PlayMode.RANDOM_SINGLE_TASK
            every { tasks } returns listOf(task1, task2, task3)
        }

        val session = mockk<Session> {
            every { title } returns "Test Session"
            every { taskGroups } returns listOf(taskGroup)
        }

        // WHEN
        val compiledSession = SessionCompiler.compile(session)

        // THEN
        assertThat(compiledSession.sessionTitle).isEqualTo("Test Session")
        assertThat(compiledSession.taskList.size).isEqualTo(1)
        assertThat(compiledSession.taskList[0].taskTitle).isIn(listOf("Task 1", "Task 2", "Task 3"))
    }

    @Test
    fun `GIVEN a taskGroup in RANDOM_N_TASKS play mode WHEN compiled THEN the compiled task sequence is correct`() {
        // GIVEN
        val task1 = mockk<Task> {
            every { id } returns 1L
            every { title } returns "Task 1"
            every { duration } returns 10.seconds
        }
        val task2 = mockk<Task> {
            every { id } returns 2L
            every { title } returns "Task 2"
            every { duration } returns 20.seconds
        }
        val task3 = mockk<Task> {
            every { id } returns 3L
            every { title } returns "Task 3"
            every { duration } returns 30.seconds
        }

        val taskGroup = mockk<TaskGroup> {
            every { title } returns "Test Group"
            every { color } returns 0xFFFF0000
            every { playMode } returns PlayMode.RANDOM_N_TASKS
            every { numberOfRandomTasks } returns 2
            every { tasks } returns listOf(task1, task2, task3)
        }

        val session = mockk<Session> {
            every { title } returns "Test Session"
            every { taskGroups } returns listOf(taskGroup)
        }

        // WHEN
        val compiledSession = SessionCompiler.compile(session)

        // THEN
        assertThat(compiledSession.sessionTitle).isEqualTo("Test Session")
        assertThat(compiledSession.taskList.size).isEqualTo(2)
        assertThat(compiledSession.taskList.map { it.taskTitle }).containsNoDuplicates()
        assertThat(
            compiledSession.taskList.map {
                it.taskTitle
            }
        ).containsAnyIn(listOf("Task 1", "Task 2", "Task 3"))
    }

    @Test
    fun `GIVEN a taskGroup in RANDOM_ALL_TASKS play mode WHEN compiled THEN the compiled task sequence is correct`() {
        // GIVEN
        val task1 = mockk<Task> {
            every { id } returns 1L
            every { title } returns "Task 1"
            every { duration } returns 10.seconds
        }
        val task2 = mockk<Task> {
            every { id } returns 2L
            every { title } returns "Task 2"
            every { duration } returns 20.seconds
        }
        val task3 = mockk<Task> {
            every { id } returns 3L
            every { title } returns "Task 3"
            every { duration } returns 30.seconds
        }

        val taskGroup = mockk<TaskGroup> {
            every { title } returns "Test Group"
            every { color } returns 0xFFFF0000
            every { playMode } returns PlayMode.RANDOM_ALL_TASKS
            every { tasks } returns listOf(task1, task2, task3)
        }

        val session = mockk<Session> {
            every { title } returns "Test Session"
            every { taskGroups } returns listOf(taskGroup)
        }

        // WHEN
        val compiledSession = SessionCompiler.compile(session)

        // THEN
        assertThat(compiledSession.sessionTitle).isEqualTo("Test Session")
        assertThat(compiledSession.taskList.size).isEqualTo(3)
        assertThat(
            compiledSession.taskList.map {
                it.taskTitle
            }
        ).containsExactlyElementsIn(listOf("Task 1", "Task 2", "Task 3"))
    }

    @Test
    fun `GIVEN a session with multiple taskGroups WHEN compiled THEN the compiled task order is correct`() {
        // GIVEN
        val task1 = mockk<Task> {
            every { id } returns 1L
            every { title } returns "Task 1"
            every { duration } returns 10.seconds
        }

        val taskGroup1 = mockk<TaskGroup> {
            every { title } returns "Group 1"
            every { color } returns 0xFFFF0000
            every { playMode } returns PlayMode.SEQUENCE
            every { tasks } returns listOf(task1)
        }

        val task3 = mockk<Task> {
            every { id } returns 3L
            every { title } returns "Task 3"
            every { duration } returns 30.seconds
        }
        val task4 = mockk<Task> {
            every { id } returns 4L
            every { title } returns "Task 4"
            every { duration } returns 40.seconds
        }

        val taskGroup2 = mockk<TaskGroup> {
            every { title } returns "Group 2"
            every { color } returns 0xFF00FF00
            every { playMode } returns PlayMode.SEQUENCE
            every { tasks } returns listOf(task3, task4)
        }

        val session = mockk<Session> {
            every { title } returns "Test Session"
            every { taskGroups } returns listOf(taskGroup1, taskGroup2)
        }

        // WHEN
        val compiledSession = SessionCompiler.compile(session)

        // THEN
        assertThat(compiledSession.sessionTitle).isEqualTo("Test Session")
        assertThat(compiledSession.taskList.size).isEqualTo(3)
        assertThat(compiledSession.totalDuration).isEqualTo(80.seconds)

        with(compiledSession.taskList[0]) {
            assertThat(taskGroupTitle).isEqualTo("Group 1")
            assertThat(taskGroupColor).isEqualTo(Color(0xFFFF0000))
            assertThat(taskTitle).isEqualTo("Task 1")
            assertThat(taskDuration).isEqualTo(10.seconds)
        }

        with(compiledSession.taskList[1]) {
            assertThat(taskGroupTitle).isEqualTo("Group 2")
            assertThat(taskGroupColor).isEqualTo(Color(0xFF00FF00))
            assertThat(taskTitle).isEqualTo("Task 3")
            assertThat(taskDuration).isEqualTo(30.seconds)
        }

        with(compiledSession.taskList[2]) {
            assertThat(taskGroupTitle).isEqualTo("Group 2")
            assertThat(taskGroupColor).isEqualTo(Color(0xFF00FF00))
            assertThat(taskTitle).isEqualTo("Task 4")
            assertThat(taskDuration).isEqualTo(40.seconds)
        }
    }
}
