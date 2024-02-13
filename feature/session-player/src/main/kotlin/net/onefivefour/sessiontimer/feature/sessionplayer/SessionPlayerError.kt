package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SessionPlayerError(
    errorMessage: String
) {
    Text(text = errorMessage)
}