package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun SessionPlayerError(errorMessage: String) {
    Text(text = errorMessage)
}
