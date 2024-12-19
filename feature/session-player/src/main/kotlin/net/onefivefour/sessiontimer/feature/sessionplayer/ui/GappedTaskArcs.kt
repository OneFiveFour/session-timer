package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.feature.sessionplayer.model.TimerState
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import net.onefivefour.sessiontimer.feature.sessionplayer.ui.modifier.arc

@Composable
internal fun GappedTaskArcs(
    uiState: UiState.Ready,
    timerState: () -> TimerState
) {
    Box(modifier = Modifier.padding(44.dp)) {
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
                timerState = timerState,
                task = task,
                width = 5.dp,
                startAngle = startAngle,
                endAngle = sweepAngle
            )

            startAngle += sweepAngle + gapAngle
        }
    }
}