package net.onefivefour.sessiontimer.feature.sessionplayer.model

import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.Session
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import kotlin.time.Duration

internal fun Session.toCompiledSession(): CompiledSession {
    val totalDuration = when {
        taskGroups.isEmpty() -> Duration.ZERO
        else -> {
            taskGroups
                .flatMap { it.tasks }
                .map { it.duration }
                .reduce { acc, duration -> acc + duration }
        }
    }

    val taskIndices = mutableListOf<Pair<Int, Int>>()
    this.taskGroups.forEachIndexed { taskGroupIndex, taskGroup ->
        taskGroup.tasks.forEachIndexed { taskIndex, _ ->
            taskIndices.add(taskGroupIndex to taskIndex)
        }
    }

    return CompiledSession(
        this.title,
        totalDuration,
        taskIndices,
        this.taskGroups.toUiTaskGroups()
    )
}

private fun List<TaskGroup>.toUiTaskGroups(): List<UiTaskGroup> {
    return this.map { taskGroup -> taskGroup.toUiTaskGroup() }
}

private fun TaskGroup.toUiTaskGroup(): UiTaskGroup {
    val tasks = when (this.playMode) {
        PlayMode.SEQUENCE -> this.tasks
        PlayMode.RANDOM_SINGLE_TASK -> this.tasks.take(1).shuffled()
        PlayMode.RANDOM_ALL_TASKS -> this.tasks.take(this.tasks.size).shuffled()
    }.map { task -> task.toUiTask() }

    return UiTaskGroup(
        this.id,
        this.title,
        this.color,
        tasks
    )
}

private fun Task.toUiTask(): UiTask {
    return UiTask(
        this.id,
        this.title,
        this.duration
    )
}
