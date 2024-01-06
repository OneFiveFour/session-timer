package net.onefivefour.sessiontimer.sessioneditor

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.theme.typography

@Composable
fun SessionEditorScreen(
    modifier: Modifier = Modifier
) {

    Text(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onBackground,
        text = "TESTI",
        style = typography.titleLarge

    )

}

@Preview
@Composable
fun SessionEditorScreenPreview() {
    SessionEditorScreen()
}