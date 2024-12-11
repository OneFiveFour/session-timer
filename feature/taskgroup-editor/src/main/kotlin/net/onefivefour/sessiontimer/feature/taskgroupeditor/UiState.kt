package net.onefivefour.sessiontimer.feature.taskgroupeditor

internal sealed interface UiState {
    data object Initial : UiState
    data class Success(val taskGroup: UiTaskGroup) : UiState
}
