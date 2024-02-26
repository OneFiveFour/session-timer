package net.onefivefour.sessiontimer.feature.sessionplayer.model

internal data class CompiledSession(
    val title : String,
    val taskGroups: List<UiTaskGroup>
)