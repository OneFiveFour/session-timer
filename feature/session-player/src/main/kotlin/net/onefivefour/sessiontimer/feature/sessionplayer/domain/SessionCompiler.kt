package net.onefivefour.sessiontimer.feature.sessionplayer.domain

import androidx.compose.ui.graphics.Color
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiSession
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTask

@Singleton
internal class SessionCompiler @Inject constructor() {

    private val mutex = Mutex()

    private var compiledSession: UiSession? = null

    suspend fun compile(session: Session): UiSession = mutex.withLock {
        compiledSession?.let {
            return it
        }

        compileSession(session).also {
            compiledSession = it
        }
    }

    private fun compileSession(session: Session): UiSession {
        val taskList = buildList {
            session.taskGroups.forEach { taskGroup ->
                addAll(compileTaskList(taskGroup))
            }
        }

        return UiSession(
            sessionTitle = session.title,
            taskList = taskList,
            totalDuration = taskList.fold(Duration.ZERO) { acc, task -> acc + task.taskDuration }
        )
    }

    private fun compileTaskList(taskGroup: TaskGroup): List<UiTask> {
        val selectedTasks = when (taskGroup.playMode) {
            PlayMode.SEQUENCE -> taskGroup.tasks
            PlayMode.RANDOM_SINGLE_TASK ->
                taskGroup.tasks
                    .shuffled()
                    .take(1)
            PlayMode.RANDOM_N_TASKS ->
                taskGroup.tasks
                    .shuffled()
                    .take(taskGroup.numberOfRandomTasks)
            PlayMode.RANDOM_ALL_TASKS ->
                taskGroup.tasks
                    .shuffled()
        }

        return selectedTasks.map { task ->
            UiTask(
                id = task.id,
                taskGroupTitle = taskGroup.title,
                taskGroupColor = Color(taskGroup.color),
                taskTitle = task.title,
                taskDuration = task.duration
            )
        }
    }

    fun reset() {
        compiledSession = null
    }
}
