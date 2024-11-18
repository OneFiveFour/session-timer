package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun SessionOverviewError(errorMessage: String) {
    Text(text = errorMessage)
}
