package net.onefivefour.sessiontimer.feature.sessionoverview

internal sealed interface UiState {
    data object Initial : UiState
    data class Success(val sessions: List<UiSession>) : UiState
    data class Error(val message: String) : UiState
}