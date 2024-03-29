package net.onefivefour.sessiontimer.feature.sessionplayer.model

import kotlin.time.Duration

internal data class UiTask(
    val id: Long,
    val title: String,
    val duration: Duration
)