package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.usecases.api.session.GetSessionUseCase
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.domain.SessionCompiler
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiSession
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState

@HiltViewModel
internal class SessionScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getSessionUseCase: GetSessionUseCase
) : ViewModel() {

    private val sessionId = savedStateHandle.toRoute<SessionPlayerRoute>().sessionId

    private lateinit var compiledSession: UiSession

    private var _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getSessionUseCase.execute(sessionId).map { session ->

                if (session == null) {
                    return@map UiState.Initial
                }

                if (!::compiledSession.isInitialized) {
                    compiledSession = SessionCompiler.compile(session)
                }

                when {
                    compiledSession.taskList.isEmpty() -> UiState.Error("Session has no tasks")
                    else -> computeUiState(compiledSession)
                }
            }.collect { newUiState ->
                _uiState.update { newUiState }
            }
        }
    }

    private fun computeUiState(compiledSession: UiSession): UiState.Ready {
        return UiState.Ready(
            sessionTitle = compiledSession.sessionTitle,
            tasks = compiledSession.taskList,
            totalDuration = compiledSession.totalDuration
        )
    }

}
