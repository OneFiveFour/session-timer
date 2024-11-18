package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.feature.sessionplayer.SessionPlayerViewModel

@Composable
fun SessionPlayerScreen(sessionId: Long) {

    val viewModel: SessionPlayerViewModel = hiltViewModel()
    val sessionPlayerState by viewModel.uiState.collectAsStateWithLifecycle()

    Log.d("+++", "sessionId: $sessionId")

    SessionPlayer(
        uiState = sessionPlayerState,
        { viewModel.onStartSession() },
        { viewModel.onPauseSession() },
        { viewModel.onResetSession() }
    )
}
