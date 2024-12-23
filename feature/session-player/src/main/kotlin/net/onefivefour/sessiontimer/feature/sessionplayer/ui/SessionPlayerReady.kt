package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.time.Duration.Companion.seconds
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessionplayer.SessionTimerViewModel
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTask

@Composable
internal fun SessionPlayerReady(uiState: UiState.Ready) {
    val timerViewModel: SessionTimerViewModel = hiltViewModel()
    val timerState by timerViewModel.uiTimerState.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        onDispose {
            timerViewModel.onDispose()
        }
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = uiState.sessionTitle,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(24.dp)
        ) {
            // grey background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.Black.copy(alpha = 0.05f))
            )

            // white background line
            CircluarProgressNeon(
                modifier = Modifier.padding(16.dp),
                strokeColor = MaterialTheme.colorScheme.background,
                strokeWidth = 7.dp,
                glowColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                glowWidth = 14.dp,
                glowRadius = 10.dp
            )

            // outer task progress
            TaskProgressArc(
                modifier = Modifier.padding(16.dp),
                uiTimerState = { timerState },
                task = null,
                width = 11.dp,
                startAngle = 0f,
                endAngle = 360f
            )

            // inner task arcs
            GappedTaskArcs(
                uiState = uiState,
                uiTimerState = { timerState }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SessionControls(
                uiTimerState = { timerState },
                onStartSession = { timerViewModel.onStartSession() },
                onPauseSession = { timerViewModel.onPauseSession() },
                onResetSession = { timerViewModel.onResetSession() },
                onNextTask = { timerViewModel.onNextTask() },
                onPreviousTask = { timerViewModel.onPreviousTask() }
            )
        }
    }
}

@Preview
@Composable
private fun SessionPlayerPreview() {
    SessionTimerTheme {
        Surface {
            val task0 = UiTask(
                id = 1L,
                taskGroupTitle = "Tonleiter 1",
                taskGroupColor = Color(0xFFFF0000),
                taskTitle = "A-Dur rechts",
                taskDuration = 60.seconds
            )
            val task1 = UiTask(
                id = 2L,
                taskGroupTitle = "Tonleiter 2",
                taskGroupColor = Color(0xFF00FF00),
                taskTitle = "A-Dur links",
                taskDuration = 40.seconds
            )
            val task2 = UiTask(
                id = 3L,
                taskGroupTitle = "Tonleiter 3",
                taskGroupColor = Color(0xFF0000FF),
                taskTitle = "A-Dur links",
                taskDuration = 10.seconds
            )
            val uiState = UiState.Ready(
                sessionTitle = "Sess",
                tasks = listOf(task0, task1, task2),
                totalDuration = 110.seconds
            )
            SessionPlayerReady(
                uiState = uiState
            )
        }
    }
}
