package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SessionPlayerScreen() {

    val viewModel: SessionPlayerViewModel = hiltViewModel()
    val sessionPlayerState by viewModel.uiState.collectAsStateWithLifecycle()

    SessionPlayer(
        uiState = sessionPlayerState,
        { viewModel.onStartSession() },
        { viewModel.onPauseSession() },
        { viewModel.onResetSession() }
    )
}