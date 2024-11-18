package net.onefivefour.sessiontimer.feature.sessioneditor.model

import kotlin.time.Duration
import net.onefivefour.sessiontimer.core.common.domain.model.Task

internal data class UiTask(
    val id: Long,
    val title: String,
    val duration: Duration
)

internal fun List<Task>.toUiTasks() = map {
    it.toUiTask()
}

internal fun Task.toUiTask() = UiTask(
    id = this.id,
    title = this.title,
    duration = this.duration
)
