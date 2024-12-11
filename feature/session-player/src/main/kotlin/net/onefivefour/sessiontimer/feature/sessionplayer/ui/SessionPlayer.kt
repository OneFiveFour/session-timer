package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import net.onefivefour.sessiontimer.feature.sessionplayer.R
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

        UiState.Finished -> {
            Text(text = stringResource(R.string.finished))
            return
        }

        is UiState.Running -> {
        }
    }

    Column {
        Text(text = uiState::class.simpleName.toString())

        Text(text = uiState.sessionTitle)

        Text(text = uiState.elapsedDuration.toString())

        Text(text = uiState.timerMode.toString())

        Text(text = uiState.currentTask.taskGroupTitle)

        val title = uiState.currentTask.taskTitle
        Text(text = title)

        val duration = uiState.currentTask.taskDuration
        Text(text = duration.toString())

        Button(onClick = onStartSession) {
            Text(text = stringResource(R.string.start))
        }
        Button(onClick = onPauseSession) {
            Text(text = stringResource(R.string.pause))
        }
        Button(onClick = onResetSession) {
            Text(text = stringResource(R.string.reset))
        }
    }
}
