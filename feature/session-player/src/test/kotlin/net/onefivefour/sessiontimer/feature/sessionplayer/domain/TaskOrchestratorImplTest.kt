package net.onefivefour.sessiontimer.feature.sessionplayer.domain

import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

class TaskOrchestratorImplTest {

    private val task1 = mockk<Task>().apply {
        every { duration } returns 10.seconds
    }

    private val task2 = mockk<Task>().apply {
        every { duration } returns 15.seconds
    }

    private val task3 = mockk<Task>().apply {
        every { duration } returns 20.seconds
    }

    private val task4 = mockk<Task>().apply {
        every { duration } returns 25.seconds
    }

    @Test
    fun `getCurrentTask returns correct task in sequential mode`() {
        val taskGroup = getTaskGroup(
            tasks = listOf(task1, task2),
            playMode = PlayMode.SEQUENCE
        )
        val orchestrator = TaskOrchestratorImpl(listOf(taskGroup))

        assertThat(orchestrator.getCurrentTask()).isEqualTo(task1)
    }

    @Test
    fun `getNextTask works correctly in sequential mode`() {
        val taskGroup = getTaskGroup(
            tasks = listOf(task1, task2),
            playMode = PlayMode.SEQUENCE
        )

        val orchestrator = TaskOrchestratorImpl(listOf(taskGroup))

        assertThat(orchestrator.getNextTask()).isEqualTo(task2)
        assertThat(orchestrator.getNextTask()).isNull()
    }

    @Test
    fun `getNextTask works correctly in random single task mode`() {
        val taskGroup = getTaskGroup(
            tasks = listOf(task1, task2, task3, task4),
            playMode = PlayMode.RANDOM_SINGLE_TASK
        )

        val orchestrator = TaskOrchestratorImpl(listOf(taskGroup))

        // Act
        val firstRandomTask = orchestrator.getNextTask()
        val secondRandomTask = orchestrator.getNextTask()

        // Assert
        assertThat(secondRandomTask).isNull()
        assertThat(listOf(task1, task2, task3, task4)).contains(firstRandomTask)
    }

    @Test
    fun `getNextTask works correctly in random all tasks mode`() {
        val taskGroup = getTaskGroup(
            tasks = listOf(task1, task2, task3, task4),
            playMode = PlayMode.RANDOM_ALL_TASKS
        )

        val orchestrator = TaskOrchestratorImpl(listOf(taskGroup))

        val tasks = mutableListOf<Task>()
        var nextTask = orchestrator.getNextTask()
        while (nextTask != null) {
            tasks.add(nextTask)
            nextTask = orchestrator.getNextTask()
        }

        assertThat(tasks.size).isEqualTo(4)
        assertThat(tasks.toSet()).isEqualTo(setOf(task1, task2, task3, task4))
    }

    @Test
    fun `onCurrentTaskFinished updates duration of finished tasks`() {
        val taskGroup = getTaskGroup(
            tasks = listOf(task1, task2),
            playMode = PlayMode.SEQUENCE
        )

        val orchestrator = TaskOrchestratorImpl(listOf(taskGroup))

        // Act
        orchestrator.onCurrentTaskFinished()

        // Assert
        assertThat(orchestrator.getDurationOfFinishedTasks()).isEqualTo(10.seconds)
    }

    @Test
    fun `empty task groups return null for current and next task`() {
        // Arrange
        val orchestrator = TaskOrchestratorImpl(emptyList())

        // Assert
        assertThat(orchestrator.getCurrentTask()).isNull()
        assertThat(orchestrator.getNextTask()).isNull()
    }

    @Test
    fun `multiple task groups work correctly in sequential mode`() {
        val taskGroup1 = getTaskGroup(
            tasks = listOf(task1, task2),
            playMode = PlayMode.SEQUENCE
        )

        val taskGroup2 = getTaskGroup(
            tasks = listOf(task3, task4),
            playMode = PlayMode.SEQUENCE
        )

        val orchestrator = TaskOrchestratorImpl(listOf(taskGroup1, taskGroup2))

        // Act & Assert
        assertThat(orchestrator.getCurrentTask()).isEqualTo(task1)
        assertThat(orchestrator.getNextTask()).isEqualTo(task2)
        assertThat(orchestrator.getNextTask()).isEqualTo(task3)
        assertThat(orchestrator.getNextTask()).isEqualTo(task4)
        assertThat(orchestrator.getNextTask()).isNull()
    }

    private fun getTaskGroup(
        tasks: List<Task>,
        playMode: PlayMode,
        numberOfRandomTasks: Int = 0
    ) = TaskGroup(
        id = 0L,
        title = "TaskGroup with ${tasks.size} tasks in PlayMode $playMode",
        color = 0xFFFF0000,
        tasks = tasks,
        playMode = playMode,
        numberOfRandomTasks = numberOfRandomTasks,
        sessionId = 0L
    )
}