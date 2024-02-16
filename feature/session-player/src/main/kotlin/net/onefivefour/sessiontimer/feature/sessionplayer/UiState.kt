package net.onefivefour.sessiontimer.feature.sessionplayer

import net.onefivefour.sessiontimer.core.common.domain.model.Session

internal sealed interface UiState {
    data object Initial : UiState

    data class Success(
        val session: Session,
        val currentTaskId: Long,
        val currentPlayerState: SessionPlayerState = SessionPlayerState.IDLE,
        val elapsedSeconds: Int = 0
    ) : UiState

    data class Error(val message: String) : UiState
}