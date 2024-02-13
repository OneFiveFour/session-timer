package net.onefivefour.sessiontimer.feature.taskgroupeditor

import net.onefivefour.sessiontimer.core.common.domain.model.Task

data class UiTask(
    val id: Long,
    val title: String
)

fun List<Task>.toUiTasks() = map {
    it.toUiTask()
}

fun Task.toUiTask() = UiTask(
    id = this.id,
    title = this.title
)