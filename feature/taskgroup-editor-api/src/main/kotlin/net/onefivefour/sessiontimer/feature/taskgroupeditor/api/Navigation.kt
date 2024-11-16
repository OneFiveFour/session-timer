package net.onefivefour.sessiontimer.feature.taskgroupeditor.api

import kotlinx.serialization.Serializable

@Serializable
data class TaskGroupEditor(
    val taskGroupId: Long
)