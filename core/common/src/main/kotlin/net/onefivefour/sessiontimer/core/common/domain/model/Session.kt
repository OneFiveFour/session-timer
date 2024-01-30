package net.onefivefour.sessiontimer.core.common.domain.model

data class Session(
    val id: Long,
    val title: String,
    val taskGroups: List<TaskGroup>
)
