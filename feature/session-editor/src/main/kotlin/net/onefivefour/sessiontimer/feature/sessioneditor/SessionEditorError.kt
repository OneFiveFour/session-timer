package net.onefivefour.sessiontimer.feature.sessioneditor

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SessionEditorError(
    errorMessage: String
) {
    Text(text = errorMessage)
}