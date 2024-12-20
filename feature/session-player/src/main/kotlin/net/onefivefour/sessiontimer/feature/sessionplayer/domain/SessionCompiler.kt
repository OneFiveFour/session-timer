package net.onefivefour.sessiontimer.feature.sessionplayer.domain

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiSession
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTask
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SessionCompiler @Inject constructor() {

    private val mutex = Mutex()

    private var compiledSession: UiSession? = null

    suspend fun compile(session: Session): UiSession {

        mutex.withLock {

            compiledSession?.let {
                return it
            }

            val taskList = mutableListOf<UiTask>()
            var totalDuration = Duration.ZERO

            for (taskGroup in session.taskGroups) {
                val (tasks, taskGroupDuration) = when (taskGroup.playMode) {
                    PlayMode.SEQUENCE -> getSequenceTaskList(
                        taskGroup = taskGroup
                    )

                    PlayMode.RANDOM_SINGLE_TASK -> getShuffledTaskList(
                        numberOfTasks = 1,
                        taskGroup = taskGroup
                    )

                    PlayMode.RANDOM_N_TASKS -> getShuffledTaskList(
                        numberOfTasks = taskGroup.numberOfRandomTasks,
                        taskGroup = taskGroup
                    )

                    PlayMode.RANDOM_ALL_TASKS -> getShuffledTaskList(
                        numberOfTasks = taskGroup.tasks.size,
                        taskGroup = taskGroup
                    )
                }

                taskList.addAll(tasks)
                totalDuration += taskGroupDuration
            }

            return UiSession(
                sessionTitle = session.title,
                taskList = taskList,
                totalDuration = totalDuration
            ).also { result ->
                compiledSession = result
            }
        }
    }

    fun reset() {
        compiledSession = null
    }

    private fun getShuffledTaskList(
        numberOfTasks: Int,
        taskGroup: TaskGroup
    ): Pair<List<UiTask>, Duration> {
        val shuffledTasks = taskGroup.tasks.shuffled().take(numberOfTasks)
        return getSequenceTaskList(taskGroup, shuffledTasks)
    }

    private fun getSequenceTaskList(
        taskGroup: TaskGroup,
        taskList: List<Task>? = null
    ): Pair<List<UiTask>, Duration> {
        val tasks = taskList ?: taskGroup.tasks

        return tasks
            .map { task ->
                UiTask(
                    id = task.id,
                    taskGroupTitle = taskGroup.title,
                    taskGroupColor = Color(taskGroup.color),
                    taskTitle = task.title,
                    taskDuration = task.duration
                )
            } to
                tasks
                    .map { it.duration }
                    .fold(Duration.ZERO, Duration::plus)
    }
}
