package net.onefivefour.sessiontimer.feature.sessionplayer

internal sealed interface UiState {
    data object Initial : UiState

    data class Success(
        val session: CompiledSession,
        val currentTaskId: Long,
        val currentPlayerState: SessionPlayerState = SessionPlayerState.IDLE,
        val elapsedSeconds: Int = 0
    ) : UiState

    data class Error(val message: String) : UiState
}