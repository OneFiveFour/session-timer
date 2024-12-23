package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.feature.sessionplayer.SessionScreenViewModel
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState

@Composable
fun SessionPlayerScreen() {
    val viewModel: SessionScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val currentUiState = uiState) {
        UiState.Initial -> {
            SessionPlayerInitial()
            return
        }

        is UiState.Error -> {
            SessionPlayerError(currentUiState.message)
            return
        }

        is UiState.Ready -> {
            SessionPlayerReady(currentUiState)
            return
        }
    }
}
