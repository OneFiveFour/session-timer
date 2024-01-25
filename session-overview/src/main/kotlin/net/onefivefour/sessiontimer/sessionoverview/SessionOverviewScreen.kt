package net.onefivefour.sessiontimer.sessionoverview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.theme.typography

@Composable
fun SessionOverviewScreen(
    onEditSession: (Long) -> Unit
) {

    val viewModel: SessionOverviewViewModel = hiltViewModel()
    val sessionOverviewState by viewModel.uiState.collectAsStateWithLifecycle()

    SessionOverview(
        uiState = sessionOverviewState,
        onEditSession = onEditSession,
        onNewSession = { viewModel.newSession() }
    )
}

@Composable
fun SessionOverviewInitial() {
    Text(text = "SessionOverview Initial")
}

@Composable
fun SessionOverviewError(
    errorMessage: String
) {
    Text(text = errorMessage)
}

@Composable
internal fun SessionOverview(
    uiState: UiState,
    onEditSession: (Long) -> Unit,
    onNewSession: () -> Unit
) {

    when (uiState) {
        UiState.Initial -> {
            SessionOverviewInitial()
            return
        }
        is UiState.Error -> {
            SessionOverviewError(uiState.message)
            return
        }
        is UiState.Success -> { }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Button(onClick = { onNewSession() }) {
            Text(text = "New Session")
        }

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