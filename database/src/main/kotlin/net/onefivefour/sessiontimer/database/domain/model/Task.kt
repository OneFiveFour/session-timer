package net.onefivefour.sessiontimer.database.domain.model

import kotlin.time.Duration

data class Task(
    val id: Long,
    val title: String?,
    val durationInSeconds: Duration?,
    val taskGroupId: Long
)
