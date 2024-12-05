package net.onefivefour.sessiontimer.feature.sessionplayer.model

import kotlin.time.Duration

data class UiCompiledTask(
    val taskGroupTitle: String,
    val taskGroupColor: Long,
    val taskTitle: String,
    val taskDuration: Duration
)
