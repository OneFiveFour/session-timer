package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.feature.sessionplayer.R
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiCompiledTask
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import kotlin.time.Duration.Companion.seconds

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
            SessionPlayerRunning(
                uiState,
                onStartSession,
                onPauseSession,
                onResetSession
            )
        }
    }
}

@Composable
private fun SessionPlayerRunning(
    uiState: UiState.Running,
    onStartSession: () -> Unit,
    onPauseSession: () -> Unit,
    onResetSession: () -> Unit
) {
    Column {
        Text(text = uiState.sessionTitle)

        Text(text = uiState.elapsedDuration.toString())

        Text(text = uiState.timerMode.toString())

        Text(text = uiState.currentTask.taskGroupTitle)

        val title = uiState.currentTask.taskTitle
        Text(text = title)

        val duration = uiState.currentTask.taskDuration
        Text(text = duration.toString())

        Row {
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
}

@Preview
@Composable
private fun SessionPlayerPreview() {
    SessionTimerTheme {
        val currentTask = UiCompiledTask(
            taskGroupTitle = "Tonleiter",
            taskGroupColor = 0xFF86e6ff,
            taskTitle = "A-Dur rechts",
            taskDuration = 60.seconds
        )
        val uiState = UiState.Running(
            sessionTitle = "Klavierübungen",
            currentTask = currentTask,
            timerMode = TimerMode.RUNNING,
            elapsedDuration = 14.seconds,
            totalDuration = 60.seconds
        )
        SessionPlayer(
            uiState = uiState,
            onStartSession = { },
            onPauseSession = { },
            onResetSession = { }
        )
    }
}
