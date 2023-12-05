package net.onefivefour.sessiontimer.sessioneditor

import android.content.res.Resources.Theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SessionEditor(
    modifier: Modifier = Modifier
) {

    Text(
        color = MaterialTheme.colorScheme.onBackground,
        text = "TESTI"
    )

}

@Preview
@Composable
fun SessionEditorPreview() {
    SessionEditor()
}