package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.core.theme.typography

@Composable
fun SessionOverviewScreen(
    onEditSession: (Long) -> Unit
) {

    val viewModel: SessionOverviewViewModel = hiltViewModel()
    val sessionOverviewState by viewModel.uiState.collectAsStateWithLifecycle()

    SessionOverview(
        uiState = sessionOverviewState,
        onEditSession = onEditSession,
        onNewSession = { viewModel.newSession() },
        onDeleteSession = { sessionId -> viewModel.deleteSession(sessionId) },
        onSetSessionTitle = { sessionId, title -> viewModel.setSessionTitle(sessionId, title) }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SessionOverview(
    uiState: UiState,
    onEditSession: (Long) -> Unit,
    onNewSession: () -> Unit,
    onDeleteSession: (Long) -> Unit,
    onSetSessionTitle: (Long, String) -> Unit
) {

    var editSessionId by remember { mutableStateOf<Long?>(null) }
    var sessionTitle by remember { mutableStateOf("") }
    var editSessionTitle by remember {
        mutableStateOf(
            TextFieldValue(
                text = sessionTitle,
                selection = TextRange(sessionTitle.length)
            )
        )
    }

    when (uiState) {
        UiState.Initial -> {
            SessionOverviewInitial()
            return
        }

        is UiState.Error -> {
            SessionOverviewError(uiState.message)
            return
        }

        is UiState.Success -> {}
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Button(onClick = { onNewSession() }) {
            Text(text = "New Session")
        }

        for (session in uiState.sessions) {

            Row {

                if (session.id == editSessionId) {

                    Row {
                        TextField(
                            value = editSessionTitle,
                            onValueChange = { editSessionTitle = it },
                            label = { Text("Title") }
                        )

                        Button(onClick = {
                            onSetSessionTitle(editSessionId!!, editSessionTitle.text)
                            editSessionId = null
                        }) {
                            Text(text = "OK")
                        }
                    }
                } else {
                    Text(
                        modifier = Modifier.combinedClickable(
                            onClick = { onEditSession(session.id) },
                            onLongClick = {
                                sessionTitle = session.title
                                editSessionId = session.id
                            }
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                        text = session.title,
                        style = typography.titleLarge
                    )
                }



                Button(onClick = { onDeleteSession(session.id) }) {
                    Text(text = "Delete")
                }
            }
        }
    }
}