package net.onefivefour.sessiontimer.feature.sessionplayer.model

import kotlin.time.Duration

internal data class UiSession(
    val sessionTitle: String,
    val totalDuration: Duration,
    val taskList: List<UiTask>
)
