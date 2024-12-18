package net.onefivefour.sessiontimer.feature.sessionplayer.model

import kotlin.time.Duration

internal sealed interface UiState {

    data object Initial : UiState

    data class Ready(
        val sessionTitle: String,
        val totalDuration: Duration,
        val tasks: List<UiTask>
    ) : UiState

    data class Error(val message: String) : UiState
}
