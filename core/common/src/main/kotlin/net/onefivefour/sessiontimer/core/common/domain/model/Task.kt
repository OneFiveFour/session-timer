package net.onefivefour.sessiontimer.core.common.domain.model

import kotlin.time.Duration

data class Task(
    val id: Long,
    val title: String?,
    val duration: Duration?,
    val taskGroupId: Long
)
