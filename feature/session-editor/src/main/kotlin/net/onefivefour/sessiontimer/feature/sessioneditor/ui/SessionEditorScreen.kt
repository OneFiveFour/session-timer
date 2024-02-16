package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.foundation.layout.fillMaxSize
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
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorViewModel
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.UiState
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask

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
        onUpdateTask = { updatedTask -> viewModel.updateTask(updatedTask) }
    )
}


@Composable
internal fun SessionEditor(
    uiState: UiState,
    onNewTaskGroup: () -> Unit,
    onNewTask: (Long) -> Unit,
    onDeleteTask: (Long) -> Unit,
    onDeleteTaskGroup: (Long) -> Unit,
    onEditTaskGroup: (Long) -> Unit,
    onUpdateTask: (UiTask) -> Unit
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

        items(
            items = session.taskGroups,
            key = { taskGroup -> taskGroup.id}
        ) { taskGroup ->

            TaskGroupItem(
                taskGroup = taskGroup,
                onNewTask = onNewTask,
                onDeleteTask = onDeleteTask,
                onDeleteTaskGroup = onDeleteTaskGroup,
                onEditTaskGroup = onEditTaskGroup,
                onUpdateTask = onUpdateTask
            )

        }
    }
}

