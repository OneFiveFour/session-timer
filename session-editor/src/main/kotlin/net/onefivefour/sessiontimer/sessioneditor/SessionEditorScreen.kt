package net.onefivefour.sessiontimer.sessioneditor

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.theme.typography

@Composable
fun SessionEditorScreen(
    uiState: UiState
) {

    val session = uiState.session ?: return

    var text = "ID: ${session.id} | TITLE: ${session.title}"
    session.taskGroups.forEach { taskGroup ->
        text += "\n--------------\n  TASK GROUP: ${taskGroup.title}"
        taskGroup.tasks.forEach { task ->
            text += "\n  TASK: ${task.title} (${task.duration.inWholeSeconds} seconds)"
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
    SessionEditorScreen(UiState())
}