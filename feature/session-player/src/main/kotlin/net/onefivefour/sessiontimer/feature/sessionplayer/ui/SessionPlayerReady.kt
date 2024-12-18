package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessionplayer.R
import net.onefivefour.sessiontimer.feature.sessionplayer.SessionTimerViewModel
import net.onefivefour.sessiontimer.feature.sessionplayer.model.TimerState
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTask
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import net.onefivefour.sessiontimer.feature.sessionplayer.ui.modifier.arc
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun SessionPlayerReady(uiState: UiState.Ready) {

    val timerViewModel: SessionTimerViewModel = hiltViewModel()
    val timerState by timerViewModel.timerState.collectAsStateWithLifecycle()

    Column {
        Text(text = uiState.sessionTitle)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(24.dp)
        ) {

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
                strokeWidth = 6.dp,
                glowColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f),
                glowWidth = 14.dp,
                glowRadius = 10.dp
            )

            Box(modifier = Modifier.padding(40.dp)) {
                val gapAngle = 5f
                var startAngle = gapAngle / 2

                uiState.tasks.forEach { task ->

                    val durationRatio = (task.taskDuration / uiState.totalDuration).toFloat()
                    val sweepAngle = 360f * durationRatio - gapAngle

                    // task indicator
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .arc(
                                color = task.taskGroupColor,
                                width = 1.dp,
                                progress = sweepAngle / 360f,
                                startAngle = startAngle
                            )
                    )

                    // task progress
                    TaskProgressArc(
                        timerState = { timerState },
                        task = task,
                        width = 5.dp,
                        startAngle = startAngle,
                        endAngle = sweepAngle / 360f
                    )

                    startAngle += sweepAngle + gapAngle
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { timerViewModel.onStartSession(timerState) }) {
                Text(text = stringResource(R.string.start))
            }
            Button(onClick = { timerViewModel.onPauseSession() }) {
                Text(text = stringResource(R.string.pause))
            }
            Button(onClick = { timerViewModel.onResetSession() }) {
                Text(text = stringResource(R.string.reset))
            }
        }
    }
}

@Composable
private fun TaskProgressArc(
    timerState: () -> TimerState,
    task: UiTask,
    width: Dp,
    startAngle: Float,
    endAngle: Float
) {
    val state = timerState()

    val progress = calculateTaskProgress(task, state)

    CircluarProgressNeon(
        strokeColor = task.taskGroupColor,
        strokeWidth = width,
        glowWidth = 10.dp,
        glowRadius = 5.dp,
        startAngle = startAngle,
        progress = endAngle * progress
    )
}

private fun calculateTaskProgress(task: UiTask, timerState: TimerState): Float {
    return when (timerState) {
        is TimerState.Finished -> 1f
        is TimerState.Initial -> 0f
        is TimerState.Ready -> when {
            task == timerState.currentTask ->
                (timerState.elapsedTaskDuration / task.taskDuration).toFloat().coerceIn(0f, 1f)

            timerState.tasks.indexOf(task) < timerState.tasks.indexOf(timerState.currentTask) -> 1f
            else -> 0f
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