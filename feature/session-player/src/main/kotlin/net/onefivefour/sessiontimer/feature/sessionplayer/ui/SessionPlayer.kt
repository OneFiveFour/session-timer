package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import net.onefivefour.sessiontimer.feature.sessionplayer.R
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import kotlin.time.Duration

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

        val title = uiState.currentTask?.title ?: stringResource(R.string.no_task_defined)
        Text(text = title)

        val duration = uiState.currentTask?.duration ?: Duration.ZERO
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
