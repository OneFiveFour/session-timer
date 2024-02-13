package net.onefivefour.sessiontimer.feature.sessioneditor

import net.onefivefour.sessiontimer.core.common.domain.model.Task
import kotlin.time.Duration

data class UiTask(
    val id: Long,
    val title: String,
    val duration: Duration
)

fun List<Task>.toUiTasks() = map {
    it.toUiTask()
}

fun Task.toUiTask() = UiTask(
    id = this.id,
    title = this.title,
    duration = this.duration
)