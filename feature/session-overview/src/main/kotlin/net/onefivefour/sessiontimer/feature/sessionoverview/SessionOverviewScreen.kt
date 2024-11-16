package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SessionOverviewScreen(
    onEditSession: (Long) -> Unit,
    onPlaySession: (Long) -> Unit
) {

    val viewModel: SessionOverviewViewModel = hiltViewModel()
    val sessionOverviewState by viewModel.uiState.collectAsStateWithLifecycle()

    SessionOverview(
        uiState = sessionOverviewState,
        onEditSession = onEditSession,
        onNewSession = { viewModel.newSession() },
        onDeleteSession = { sessionId -> viewModel.deleteSession(sessionId) },
        onSetSessionTitle = { sessionId, title -> viewModel.setSessionTitle(sessionId, title) },
        onStartSession = onPlaySession
    )
}