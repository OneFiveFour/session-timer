package net.onefivefour.sessiontimer.feature.sessioneditor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.core.common.domain.model.Task
import net.onefivefour.sessiontimer.core.theme.typography
import kotlin.time.Duration

@Composable
fun SessionEditorScreen(onEditTaskGroup: (Long) -> Unit) {

    val viewModel: SessionEditorViewModel = hiltViewModel()
    val sessionEditorState by viewModel.uiState.collectAsStateWithLifecycle()

    SessionEditor(
        uiState = sessionEditorState,
        onNewTaskGroup = { viewModel.newTaskGroup() },
        onNewTask = { taskGroupId -> viewModel.newTask(taskGroupId) },
        onDeleteTask = { taskId -> viewModel.deleteTask(taskId) },
        onDeleteTaskGroup = { taskGroupId -> viewModel.deleteTaskGroup(taskGroupId) },
        onEditTaskGroup = onEditTaskGroup,
        onSetTaskDuration = { durationInSeconds, taskId -> viewModel.setTaskDuration(durationInSeconds, taskId) },
        onSetTaskTitle = { taskTitle, taskId -> viewModel.setTaskTitle(taskTitle, taskId) }
    )
}

@Composable
fun SessionEditorInitial() {
    Text(text = "Session Editor Initial")
}

@Composable
fun SessionEditorError(
    errorMessage: String
) {
    Text(text = errorMessage)
}

@Composable
internal fun SessionEditor(
    uiState: UiState,
    onNewTaskGroup: () -> Unit,
    onNewTask: (Long) -> Unit,
    onDeleteTask: (Long) -> Unit,
    onDeleteTaskGroup: (Long) -> Unit,
    onEditTaskGroup: (Long) -> Unit,
    onSetTaskDuration: (Long, Long) -> Unit,
    onSetTaskTitle: (String, Long) -> Unit
) {

    when (uiState) {
        UiState.Initial -> {
            SessionEditorInitial()
            return
        }

        is UiState.Error -> {
            SessionEditorError(uiState.message)
            return
        }

        is UiState.Success -> {
            checkNotNull(uiState.session)
        }
    }

    val session = uiState.session

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        item {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = "ID: ${session.id} | TITLE: ${session.title}",
                style = typography.titleLarge
            )
        }

        item {
            Button(
                modifier = Modifier.wrapContentSize(),
                onClick = { onNewTaskGroup() }
            ) {
                Text(text = "Create new TaskGroup")
            }
        }

        items(session.taskGroups) { taskGroup ->

            Row {

                Text(
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
            // height for the LazyCol it is allowed. So we must compute the expected
            // height ahead of time.
            val tasksHeightSum = 64.dp * taskGroup.tasks.size
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false,
                modifier = Modifier.height(tasksHeightSum)
            ) {
                items(
                    count = taskGroup.tasks.size,
                    key = { index -> taskGroup.tasks[index].id },
                ) { index ->
                    val task = taskGroup.tasks[index]
                    TaskItem(
                        task,
                        { taskId, duration -> onSetTaskDuration(taskId, duration) },
                        { taskId -> onDeleteTask(taskId) }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onSetTaskDuration: (Long, Long) -> Unit,
    onDeleteTask: (Long) -> Unit
) {

    var taskDuration by remember {
        mutableStateOf(task.duration?.inWholeSeconds?.toString() ?: "0")
    }
    var taskTitle by remember {
        mutableStateOf(task.title.toString())
    }

    Row(modifier = Modifier.height(56.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(0.5f),
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
            onClick = { onSetTaskDuration(taskDuration.toLong(), task.id) }
        ) {
            Text(text = "OK")
        }
    }


}