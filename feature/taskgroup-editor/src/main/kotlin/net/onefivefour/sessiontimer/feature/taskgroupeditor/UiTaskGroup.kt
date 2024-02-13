package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.ui.graphics.Color
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup

data class UiTaskGroup(
    val id: Long,
    val title: String,
    val color: Color,
    val tasks: List<UiTask>
)

fun TaskGroup.toUiTaskGroup() = UiTaskGroup(
    id = this.id,
    title = this.title,
    color = Color(this.color),
    tasks = this.tasks.toUiTasks()
)