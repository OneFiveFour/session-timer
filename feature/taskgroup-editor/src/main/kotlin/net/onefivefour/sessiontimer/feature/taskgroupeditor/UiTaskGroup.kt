package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.ui.graphics.Color
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup

internal data class UiTaskGroup(
    val id: Long,
    val title: String,
    val color: Color,
    val playMode: PlayMode,
    val numberOfRandomTasks: Int,
    val tasks: List<UiTask>
)

internal fun TaskGroup.toUiTaskGroup() = UiTaskGroup(
    id = this.id,
    title = this.title,
    color = Color(this.color),
    playMode = this.playMode,
    numberOfRandomTasks = this.numberOfRandomTasks,
    tasks = this.tasks.toUiTasks()
)