package net.onefivefour.sessiontimer.feature.taskgroupeditor.api

import kotlinx.serialization.Serializable

@Serializable
data class TaskGroupEditorRoute(
    val taskGroupId: Long
)