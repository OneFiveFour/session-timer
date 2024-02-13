package net.onefivefour.sessiontimer.feature.sessioneditor

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.common.domain.model.Task


internal val TASK_ITEM_HEIGHT = 64.dp

@Composable
fun TaskItem(
    task: Task,
    onSetTaskTitle: (Long, String) -> Unit,
    onSetTaskDuration: (Long, Long) -> Unit,
    onDeleteTask: (Long) -> Unit
) {

    var taskDuration by remember {
        mutableStateOf(task.duration.inWholeSeconds.toString())
    }
    var taskTitle by remember {
        mutableStateOf(task.title.toString())
    }

    Row(modifier = Modifier.height(TASK_ITEM_HEIGHT)) {
        TextField(
            modifier = Modifier.fillMaxWidth(0.4f),
            value = taskTitle,
            onValueChange = { taskTitle = it }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(0.3f),
            value = taskDuration,
            onValueChange = { taskDuration = it },
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
                onSetTaskTitle(task.id, taskTitle)
                onSetTaskDuration(task.id, taskDuration.toLong())
            }
        ) {
            Text(text = "OK")
        }
    }


}