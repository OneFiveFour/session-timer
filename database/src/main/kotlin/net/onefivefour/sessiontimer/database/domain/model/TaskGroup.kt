package net.onefivefour.sessiontimer.database.domain.model

data class TaskGroup(
    val id: Long,
    val title: String,
    val color: Int,
    val tasks: List<Task>
)
