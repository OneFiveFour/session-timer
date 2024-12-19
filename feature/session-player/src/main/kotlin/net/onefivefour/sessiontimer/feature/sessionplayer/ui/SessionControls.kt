package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessionplayer.R
import net.onefivefour.sessiontimer.feature.sessionplayer.model.TimerState
import net.onefivefour.sessiontimer.feature.sessionplayer.ui.modifier.addSessionControls

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

    val playButtonIconRes = when {
        isRunning -> R.drawable.ic_pause
        else -> R.drawable.ic_play
    }

    val playButtonAction = when {
        isRunning -> onPauseSession
        else -> onStartSession
    }

    Row(
        modifier = Modifier
            .addSessionControls()
            .padding(vertical = 14.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.clickable { onPreviousTask() },
            painter = painterResource(R.drawable.ic_previous_task),
            contentDescription = stringResource(R.string.previous_task),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(52.dp))

        Icon(
            modifier = Modifier.clickable { playButtonAction() },
            painter = painterResource(playButtonIconRes),
            contentDescription = stringResource(playButtonTextRes),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(52.dp))

        Icon(
            modifier = Modifier.clickable { onNextTask() },
            painter = painterResource(R.drawable.ic_next_task),
            contentDescription = stringResource(R.string.next_task),
            tint = MaterialTheme.colorScheme.onSurface
        )
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