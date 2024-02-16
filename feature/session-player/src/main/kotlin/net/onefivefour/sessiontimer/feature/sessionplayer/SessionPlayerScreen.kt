package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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

@Composable
internal fun SessionPlayer(
    uiState: UiState,
    onStartSession: () -> Unit,
    onPauseSession: () -> Unit,
    onResetSession: () -> Unit
) {

    when (uiState) {
        UiState.Initial -> {
            SessionPlayerInitial()
            return
        }

        is UiState.Error -> {
            SessionPlayerError(uiState.message)
            return
        }

        is UiState.Success -> {
            checkNotNull(uiState.session)
        }
    }

    val session = uiState.session

    Column {

        Text(text = session.title)

        Text(text = uiState.elapsedSeconds.toString())

        Text(text = uiState.currentPlayerState.toString())

        Button(onClick = onStartSession) {
            Text(text = "Start")
        }
        Button(onClick = onPauseSession) {
            Text(text = "Pause")
        }
        Button(onClick = onResetSession) {
            Text(text = "Reset")
        }
    }

}