package net.onefivefour.sessiontimer.database.domain.model

data class Session(
    val id: Long,
    val title: String,
    val taskGroups: List<TaskGroup>
)
