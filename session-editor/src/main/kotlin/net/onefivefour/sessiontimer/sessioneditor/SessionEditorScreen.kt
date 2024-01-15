package net.onefivefour.sessiontimer.sessioneditor

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.theme.typography

@Composable
fun SessionEditorScreen(
    sessionId: Long
) {

    val viewModel: SessionEditorViewModel = hiltViewModel()
    val sessionEditorState = viewModel.uiState.collectAsStateWithLifecycle().value

    val session = sessionEditorState.session ?: return

    var text = "ID: ${session.id} | TITLE: ${session.title}"
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

@Preview
@Composable
fun SessionEditorScreenPreview() {
    SessionEditorScreen(1L)
}