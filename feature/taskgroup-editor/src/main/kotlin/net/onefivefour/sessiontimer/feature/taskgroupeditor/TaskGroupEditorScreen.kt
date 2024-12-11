package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TaskGroupEditorScreen() {
    val viewModel: TaskGroupEditorViewModel = hiltViewModel()
    val taskGroupEditorState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskGroupEditor(
        uiState = taskGroupEditorState,
        onUpdateTaskGroup = { updatedTaskGroup -> viewModel.updateTaskGroup(updatedTaskGroup) }
    )
}
