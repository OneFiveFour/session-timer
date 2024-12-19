package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.feature.sessionplayer.model.TimerState
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiTask

@Composable
internal fun TaskProgressArc(
    modifier: Modifier = Modifier,
    timerState: () -> TimerState,
    task: UiTask?,
    width: Dp,
    startAngle: Float,
    endAngle: Float
) {

    fun calculateTaskProgress(task: UiTask, timerState: TimerState): Float {
        return when (timerState) {
            is TimerState.Finished -> 1f
            is TimerState.Initial -> 0f
            is TimerState.Active -> when {
                task == timerState.currentTask ->
                    (timerState.elapsedTaskDuration / task.taskDuration).toFloat().coerceIn(0f, 1f)

                timerState.tasks.indexOf(task) < timerState.tasks.indexOf(timerState.currentTask) ->
                    1f

                else ->
                    0f
            }
        }
    }

    val state = timerState()

    if (task == null && state !is TimerState.Active) return

    val t = task ?: (state as TimerState.Active).currentTask ?: return
    val glowScale = if (task == null) 1.03f else 1f
    val glowWidth = if (task == null) 15.dp else 10.dp
    val glowRadius = if (task == null) 15.dp else 8.dp

    val progress = calculateTaskProgress(t, state)

    CircluarProgressNeon(
        modifier = modifier,
        strokeColor = t.taskGroupColor,
        strokeWidth = width,
        glowWidth = glowWidth,
        glowRadius = glowRadius,
        glowScale = glowScale,
        startAngle = startAngle,
        progress = (endAngle / 360f) * progress
    )
}