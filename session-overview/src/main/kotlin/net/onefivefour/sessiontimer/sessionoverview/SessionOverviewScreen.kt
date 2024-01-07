package net.onefivefour.sessiontimer.sessionoverview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.onefivefour.sessiontimer.theme.typography

@Composable
fun SessionOverviewScreen(
    uiState: UiState,
    onEditSession: (Long) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {

        for (session in uiState.sessions) {
            Text(
                modifier = Modifier.clickable { onEditSession(session.id) },
                color = MaterialTheme.colorScheme.onBackground,
                text = session.title,
                style = typography.titleLarge
            )
        }
    }

}

@Preview
@Composable
fun SessionOverviewScreenPreview() {
    SessionOverviewScreen(
        uiState = UiState()
    ) {}
}