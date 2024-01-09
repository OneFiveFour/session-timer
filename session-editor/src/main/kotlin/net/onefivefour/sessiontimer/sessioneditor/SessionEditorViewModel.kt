package net.onefivefour.sessiontimer.sessioneditor

import androidx.lifecycle.SavedStateHandle
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
import net.onefivefour.sessiontimer.sessioneditor.navigation.SessionEditorNavigation
import javax.inject.Inject

@HiltViewModel
class SessionEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFullSessionUseCase: GetFullSessionUseCase
) : ViewModel() {

    private val sessionId =
        checkNotNull(savedStateHandle.get<Long>(SessionEditorNavigation.KEY_SESSION_ID))

    private var _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val fullSession = getFullSessionUseCase.execute(sessionId)
            _uiState.update {
                it.copy(session = fullSession)
            }
        }
    }
}

data class UiState(
    val session: Session? = null
)