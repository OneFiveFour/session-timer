package net.onefivefour.sessiontimer.core.database.domain.model

data class TaskGroup(
    val id: Long,
    val title: String,
    val color: Int,
    val tasks: List<Task>,
    val sessionId: Long
)
