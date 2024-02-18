package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel.SessionEditorViewModel

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


