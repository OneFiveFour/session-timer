package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun SessionEditorError(errorMessage: String) {
    Text(text = errorMessage)
}
