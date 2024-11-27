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

        UiState.Finished -> {
            Text(text = stringResource(R.string.finished))
            return
        }

        is UiState.Running -> {
            checkNotNull(uiState.uiSession)
        }

    }

    val session = uiState.uiSession

    Column {
        Text(text = session.title)

        Text(text = uiState.elapsedDuration.toString())

        Text(text = uiState.timerMode.toString())

        val title = uiState.currentUiTask.title
        Text(text = title)

        val duration = uiState.currentUiTask.duration
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
