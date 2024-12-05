package net.onefivefour.sessiontimer.feature.sessionplayer.model

import kotlin.time.Duration

internal data class UiCompiledSession(
    val sessionTitle: String,
    val totalDuration: Duration,
    val taskList: List<UiCompiledTask>
)
