package net.onefivefour.sessiontimer.feature.sessionplayer.model

import kotlin.time.Duration

internal data class CompiledSession(
    val title : String,
    val totalDuration: Duration,
    val taskIndices : List<Pair<Int, Int>> = emptyList(),
    val taskGroups: List<UiTaskGroup>
)