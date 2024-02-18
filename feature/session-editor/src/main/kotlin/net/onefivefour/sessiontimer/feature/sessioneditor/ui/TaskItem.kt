package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


internal val TASK_ITEM_HEIGHT = 64.dp

@Composable
internal fun TaskItem(
    task: Task,
    onUpdateTask: (UiTask) -> Unit,
    onDeleteTask: (Long) -> Unit
) {

    var taskDuration by remember {
        mutableStateOf(task.duration)
    }
    var taskTitle by remember {
        mutableStateOf(task.title)
    }

    Row(modifier = Modifier.height(TASK_ITEM_HEIGHT)) {
        TextField(
            modifier = Modifier.fillMaxWidth(0.4f),
            value = taskTitle,
            onValueChange = { taskTitle = it }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(0.3f),
            value = taskDuration.inWholeSeconds.toString(),
            onValueChange = {
                taskDuration = if (it.isEmpty()) {
                        0.seconds
                } else {
                    Duration.parse("${it}s")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Button(
            onClick = { onDeleteTask(task.id) }
        ) {
            Text(text = "Delete")
        }
        Button(
            onClick = {
                val updatedTask = UiTask(
                    task.id,
                    taskTitle,
                    taskDuration
                )
                onUpdateTask(updatedTask)
            }
        ) {
            Text(text = "OK")
        }
    }


}