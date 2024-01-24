package net.onefivefour.sessiontimer.sessionoverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.database.domain.SessionRepository
import net.onefivefour.sessiontimer.database.domain.model.Session
import javax.inject.Inject

@HiltViewModel
internal class SessionOverviewViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
): ViewModel() {

    private var _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            sessionRepository.getAll().collectLatest { sessions ->
                _uiState.update { it.copy(sessions = sessions) }
            }
        }
    }

}

internal data class UiState(
    val sessions: List<Session> = emptyList()
)