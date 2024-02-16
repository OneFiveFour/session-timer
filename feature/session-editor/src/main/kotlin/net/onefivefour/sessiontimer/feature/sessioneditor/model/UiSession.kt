package net.onefivefour.sessiontimer.feature.sessioneditor.model

import net.onefivefour.sessiontimer.core.common.domain.model.Session

data class UiSession(
    val id: Long,
    val title: String,
    val taskGroups: List<UiTaskGroup>
)

fun Session.toUiSession() = UiSession(
    id = this.id,
    title = this.title,
    taskGroups = this.taskGroups.toUiTaskGroups()
)