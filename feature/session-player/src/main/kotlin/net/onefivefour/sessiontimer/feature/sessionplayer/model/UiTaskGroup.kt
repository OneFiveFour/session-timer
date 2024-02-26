package net.onefivefour.sessiontimer.feature.sessionplayer.model

internal data class UiTaskGroup(
    val id: Long,
    val title: String,
    val color: Long,
    val tasks: List<UiTask>
)