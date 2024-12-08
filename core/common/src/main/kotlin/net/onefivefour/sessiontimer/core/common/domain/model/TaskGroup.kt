package net.onefivefour.sessiontimer.core.common.domain.model

data class TaskGroup(
    val id: Long,
    val title: String,
    val color: Long,
    val playMode: PlayMode,
    val tasks: List<Task>,
    val numberOfRandomTasks: Int = 0,
    val sessionId: Long
)
