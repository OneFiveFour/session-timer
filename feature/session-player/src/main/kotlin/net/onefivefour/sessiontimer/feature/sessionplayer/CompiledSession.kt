package net.onefivefour.sessiontimer.feature.sessionplayer

internal data class CompiledSession(
    val title : String,
    val taskGroups: List<UiTaskGroup>
)