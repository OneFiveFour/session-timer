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
    private val getAllSessionsUseCase: GetAllSessionsUseCase,
    private val newSessionUseCase: NewSessionUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllSessionsUseCase.execute().collectLatest { sessions ->
                _uiState.update {
                    UiState.Success(sessions)
                }
            }
        }
    }

    fun newSession() {
        viewModelScope.launch {
            newSessionUseCase.execute()
        }
    }

}

internal sealed interface UiState {
    data object Initial : UiState
    data class Success(val sessions: List<Session>) : UiState
    data class Error(val message: String) : UiState
}