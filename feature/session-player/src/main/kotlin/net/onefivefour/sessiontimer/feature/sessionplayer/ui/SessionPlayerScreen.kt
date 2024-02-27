package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.feature.sessionplayer.SessionPlayerViewModel
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerScreen
import javax.inject.Inject

class SessionPlayerScreenImpl @Inject constructor() : SessionPlayerScreen {

    @Composable
    override operator fun invoke(): @Composable () -> Unit {
        val viewModel: SessionPlayerViewModel = hiltViewModel()
        val sessionPlayerState by viewModel.uiState.collectAsStateWithLifecycle()

        return {
            SessionPlayer(
                uiState = sessionPlayerState,
                { viewModel.onStartSession() },
                { viewModel.onPauseSession() },
                { viewModel.onResetSession() }
            )
        }
    }
}
