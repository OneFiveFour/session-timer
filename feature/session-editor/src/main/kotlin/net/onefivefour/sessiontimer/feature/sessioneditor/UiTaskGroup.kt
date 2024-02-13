package net.onefivefour.sessiontimer.feature.sessioneditor

import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup

data class UiTaskGroup(
    val id: Long,
    val title: String,
    val color: Int,
    val tasks: List<UiTask>
)

fun List<TaskGroup>.toUiTaskGroups() = map {
    it.toUiTaskGroup()
}

fun TaskGroup.toUiTaskGroup() = UiTaskGroup(
    id = this.id,
    title = this.title,
    color = this.color,
    tasks = this.tasks.toUiTasks()
)