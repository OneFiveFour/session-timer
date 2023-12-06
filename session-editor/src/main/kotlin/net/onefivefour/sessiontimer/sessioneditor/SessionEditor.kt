package net.onefivefour.sessiontimer.sessioneditor

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.theme.typography

@Composable
fun SessionEditor(
    modifier: Modifier = Modifier
) {

    Text(
        color = MaterialTheme.colorScheme.onBackground,
        text = "TESTI",
        style = typography.titleLarge

    )

}

@Preview
@Composable
fun SessionEditorPreview() {
    SessionEditor()
}