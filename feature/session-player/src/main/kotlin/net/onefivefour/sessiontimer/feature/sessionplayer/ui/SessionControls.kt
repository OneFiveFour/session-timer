package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import net.onefivefour.sessiontimer.feature.sessionplayer.ui.modifier.clickableWithUnboundRipple

@Composable
internal fun SessionControls(
    timerState: () -> TimerState,
    onStartSession: () -> Unit,
    onPauseSession: () -> Unit,
    onResetSession: () -> Unit,
    onNextTask: () -> Unit,
    onPreviousTask: () -> Unit
) {
    val state = timerState()
    val isRunning = when (state) {
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
        else -> when {
            state is TimerState.Finished -> R.drawable.ic_reset
            else -> R.drawable.ic_play
        }
    }

    val playButtonAction = when {
        isRunning -> onPauseSession
        else ->  when {
            state is TimerState.Finished -> onResetSession
            else -> onStartSession
        }
    }

    Row(
        modifier = Modifier
            .addSessionControls()
            .padding(vertical = 14.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.clickableWithUnboundRipple { onPreviousTask() },
            painter = painterResource(R.drawable.ic_previous_task),
            contentDescription = stringResource(R.string.previous_task),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(52.dp))

        Icon(
            modifier = Modifier.clickableWithUnboundRipple { playButtonAction() },
            painter = painterResource(playButtonIconRes),
            contentDescription = stringResource(playButtonTextRes),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(52.dp))

        Icon(
            modifier = Modifier.clickableWithUnboundRipple { onNextTask() },
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
                {},
                {}
            )
        }
    }
}