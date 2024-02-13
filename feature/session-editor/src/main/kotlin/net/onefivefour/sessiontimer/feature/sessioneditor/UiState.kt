package net.onefivefour.sessiontimer.feature.sessioneditor

import net.onefivefour.sessiontimer.core.common.domain.model.Session

internal sealed interface UiState {
    data object Initial : UiState
    data class Success(val session: Session) : UiState
    data class Error(val message: String) : UiState
}