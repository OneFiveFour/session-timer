package net.onefivefour.sessiontimer.feature.taskgroupeditor

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TaskGroupEditorScreen(taskGroupId: Long) {

    val viewModel: TaskGroupEditorViewModel = hiltViewModel()
    val taskGroupEditorState by viewModel.uiState.collectAsStateWithLifecycle()

    Log.d("+++", "TaskGroupId: $taskGroupId")

    TaskGroupEditor(
        uiState = taskGroupEditorState,
        onUpdateTaskGroup = { updatedTaskGroup -> viewModel.updateTaskGroup(updatedTaskGroup) }
    )
}