package net.onefivefour.sessiontimer.feature.sessionplayer.model

import android.graphics.Color
import com.google.common.truth.Truth.assertThat
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import org.junit.Test
import kotlin.time.Duration

class UiStateMapperTest {

    @Test
    fun `toCompiledSession without taskGroups should return session with ZERO duration`() {
        val session = Session(
            id = 1,
            title = "Session 1",
            taskGroups = emptyList()
        )

        val result = session.toCompiledSession()

        assert(result.totalDuration == Duration.ZERO)
    }

    @Test
    fun `toCompiledSession with multiple taskGroups should return session with accumulated duration`() {
        val session = Session(
            id = 1,
            title = "Session 1",
            taskGroups = listOf(
                generateTaskGroup(1L),
                generateTaskGroup(2L)
            )
        )

        val result = session.toCompiledSession()

        // assert 12 seconds, because 2 taskGroups x (1 + 2 + 3) seconds
        assertThat(result.totalDuration).isEqualTo(Duration.parse("12s"))
    }

    @Test
    fun `toCompiledSession with multiple taskGroups returns correct taskIndices`() {
        val session = Session(
            id = 1,
            title = "Session 1",
            taskGroups = listOf(
                generateTaskGroup(2L),
                generateTaskGroup(3L)
            )
        )

        val result = session.toCompiledSession()

        assertThat(result.taskIndices).isEqualTo(
            listOf(
                0 to 0,
                0 to 1,
                0 to 2,
                1 to 0,
                1 to 1,
                1 to 2
            )
        )
    }
}

private fun generateTaskGroup(id: Long) = TaskGroup(
    id = id,
    title = "TaskGroup 1",
    color = Color.RED.toLong(),
    playMode = PlayMode.RANDOM,
    numberOfRandomTasks = 1,
    tasks = generateTaskList(3, id),
    sessionId = 1L
)

fun generateTaskList(numberOfTasks: Int, taskGroupId: Long): List<Task> {
    return (1..numberOfTasks).map {
        Task(
            id = it.toLong(),
            title = "Task $it",
            duration = Duration.parse("${it}s"),
            taskGroupId = taskGroupId
        )
    }
}
