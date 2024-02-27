package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState

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

        Text(text = uiState.timerMode.toString())

        Text(text = uiState.currentTask.title)

        Text(text = uiState.currentTask.duration.toString())

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