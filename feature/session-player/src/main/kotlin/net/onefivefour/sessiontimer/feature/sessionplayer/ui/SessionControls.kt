package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessionplayer.R
import net.onefivefour.sessiontimer.feature.sessionplayer.model.TimerState

@Composable
internal fun SessionControls(
    timerState: () -> TimerState,
    onStartSession: () -> Unit,
    onPauseSession: () -> Unit,
    onNextTask: () -> Unit,
    onPreviousTask: () -> Unit
) {

    val isRunning = when (val state = timerState()) {
        is TimerState.Active -> state.isRunning
        TimerState.Finished -> false
        is TimerState.Initial -> false
    }

    val playButtonTextRes = when {
        isRunning -> R.string.pause
        else -> R.string.start
    }

    val playButtonAction = when {
        isRunning -> onPauseSession
        else -> onStartSession
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Button(onClick = onPreviousTask) {
            Text(text = stringResource(R.string.previous_task))
        }

        Button(onClick = playButtonAction) {
            Text(text = stringResource(playButtonTextRes))
        }

        Button(onClick = onNextTask) {
            Text(text = stringResource(R.string.next_task))
        }
    }
}

@Preview
@Composable
private fun SessionControlsInitialPreview() {
    SessionTimerTheme {
        Surface {
            SessionControls(
                { TimerState.Initial() },
                {},
                {},
                {},
                {}
            )
        }
    }
}