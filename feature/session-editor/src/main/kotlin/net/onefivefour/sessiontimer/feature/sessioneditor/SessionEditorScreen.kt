package net.onefivefour.sessiontimer.feature.sessioneditor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.core.theme.typography

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
        onEditTaskGroup = onEditTaskGroup
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
    onEditTaskGroup: (Long) -> Unit
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

    LazyColumn(modifier = Modifier.fillMaxHeight()) {

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
                    text = "\n  TASK GROUP: ${taskGroup.id}",
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

            taskGroup.tasks.forEach { task ->

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()) {

                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "\n    TASK ${task.id}",
                        style = typography.titleLarge
                    )

                    Button(
                        modifier = Modifier.wrapContentSize(),
                        onClick = { onDeleteTask(task.id) }
                    ) {
                        Text(text = "Delete")
                    }
                }

            }
        }
    }
}