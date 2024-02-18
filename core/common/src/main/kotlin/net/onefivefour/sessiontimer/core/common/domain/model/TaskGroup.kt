package net.onefivefour.sessiontimer.core.common.domain.model

data class TaskGroup(
    val id: Long,
    val title: String,
    val color: Long,
    val playMode: PlayMode,
    val numberOfRandomTasks: Int,
    val tasks: List<Task>,
    val sessionId: Long
)

