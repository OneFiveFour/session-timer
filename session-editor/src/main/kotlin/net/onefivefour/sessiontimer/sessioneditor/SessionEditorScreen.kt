package net.onefivefour.sessiontimer.sessioneditor

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.theme.typography

@Composable
fun SessionEditorScreen() {

    val viewModel: SessionEditorViewModel = hiltViewModel()
    val sessionEditorState = viewModel.uiState.collectAsStateWithLifecycle().value

    SessionEditor(
        uiState = sessionEditorState,
        onNewTaskGroup = { viewModel.newTaskGroup() }
        )
}


@Composable
fun SessionEditor(
    uiState: UiState,
    onNewTaskGroup: () -> Unit
) {
    val session = uiState.session ?: return

    var text = "ID: ${session.id} | TITLE: ${session.title}"

    Button(
        modifier = Modifier.wrapContentSize(),
        onClick = { onNewTaskGroup() }
    ) {
        Text(text = "Create new TaskGroup")
    }

    session.taskGroups.forEach { taskGroup ->
        text += "\n--------------\n  TASK GROUP: ${taskGroup.title}"
        taskGroup.tasks.forEach { task ->
            text += "\n  TASK: ${task.title} (${task.durationInSeconds.inWholeSeconds} seconds)"
        }
    }

    Text(
        color = MaterialTheme.colorScheme.onBackground,
        text = text,
        style = typography.titleLarge

    )
}