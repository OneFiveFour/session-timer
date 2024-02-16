package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.theme.typography
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask


@Composable
fun TaskGroupItem(
    taskGroup: TaskGroup,
    onNewTask: (Long) -> Unit,
    onDeleteTask: (Long) -> Unit,
    onDeleteTaskGroup: (Long) -> Unit,
    onEditTaskGroup: (Long) -> Unit,
    onUpdateTask: (UiTask) -> Unit
) {
    Row {

        val taskGroupColor = Color(taskGroup.color)
        Text(
            modifier = Modifier.background(taskGroupColor),
            color = MaterialTheme.colorScheme.onBackground,
            text = "\n  ${taskGroup.title}",
            style = typography.titleLarge
        )

        Button(
            modifier = Modifier.wrapContentSize(),
            onClick = { onEditTaskGroup(taskGroup.id) }
        ) {
            Text(text = "Edit")
        }

        Button(
            modifier = Modifier.wrapContentSize(),
            onClick = { onDeleteTaskGroup(taskGroup.id) }
        ) {
            Text(text = "Delete")
        }
    }

    Button(
        modifier = Modifier.wrapContentSize(),
        onClick = { onNewTask(taskGroup.id) }
    ) {
        Text(text = "Create new Task")
    }


    // We want to animate item placement which is only possible out of the box
    // with a LazyColumn. Nested scrollables are not allowed in Jetpack Compose
    // due to single layout pass requirements, however if we have a specified
    // height for the LazyColumn it is allowed. So we must compute the expected
    // height ahead of time.
    val tasksHeightSum = TASK_ITEM_HEIGHT * taskGroup.tasks.size
    LazyColumn(
        userScrollEnabled = false,
        modifier = Modifier.height(tasksHeightSum)
    ) {
        items(
            items = taskGroup.tasks,
            key = { task -> task.id },
        ) { task ->

            TaskItem(
                task,
                { updatedTask -> onUpdateTask(updatedTask) },
                { taskId -> onDeleteTask(taskId) }
            )

        }
    }
}