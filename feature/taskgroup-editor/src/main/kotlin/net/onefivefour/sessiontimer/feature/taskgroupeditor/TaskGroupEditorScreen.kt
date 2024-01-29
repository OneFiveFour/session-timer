package net.onefivefour.sessiontimer.taskgroupeditor

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.feature.taskgroupeditor.TaskGroupEditorViewModel
import net.onefivefour.sessiontimer.feature.taskgroupeditor.UiState

@Composable
fun TaskGroupEditorScreen() {

    val viewModel: TaskGroupEditorViewModel = hiltViewModel()
    val taskGroupEditorState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskGroupEditor(
        uiState = taskGroupEditorState
    )
}


@Composable
internal fun TaskGroupEditor(
    uiState: UiState
) {

    when (uiState) {
        UiState.Initial -> Text(text = "TaskGroup Editor")
        is UiState.Success -> Text(text = uiState.taskGroup.title)
    }



}