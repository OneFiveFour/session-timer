package net.onefivefour.sessiontimer.feature.sessionplayer.model

import kotlin.time.Duration

internal data class UiSession(
    val title: String,
    val totalDuration: Duration,

    /**
     * List of pairs representing <taskGroupIndex, taskIndex>
     */
    val taskGroupAndTaskIndices: List<Pair<Int, Int>> = emptyList(),
    val taskGroups: List<UiTaskGroup>
)
