package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.components.button.PrimaryButton
import net.onefivefour.sessiontimer.core.ui.R as UiR

@Composable
internal fun SessionOverview(
    uiState: UiState,
    onEditSession: (Long) -> Unit,
    onNewSession: () -> Unit,
    onDeleteSession: (Long) -> Unit,
    onSetSessionTitle: (Long, String) -> Unit,
    onStartSession: (Long) -> Unit
) {

    if (uiState == UiState.Initial) {
        SessionOverviewInitial()
        return
    }

    if (uiState is UiState.Error) {
        SessionOverviewError(uiState.message)
        return
    }

    if (uiState !is UiState.Success) {
        return
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.sessions),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.padding(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            items(uiState.sessions) { session ->
                SessionItem(
                    session,
                    onEditSession,
                    onStartSession
                )
            }
        }


        Spacer(modifier = Modifier.padding(16.dp))


        PrimaryButton(
            text = stringResource(id = R.string.new_session),
            iconRes = UiR.drawable.ic_add,
            contentDescription = stringResource(id = R.string.new_session)
        ) {
            onNewSession()
        }

        Spacer(modifier = Modifier.padding(16.dp))
    }
}



@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun SessionOverviewPreview() {
    SessionTimerTheme {
        SessionOverview(
            uiState = UiState.Success(
                listOf(
                    UiSession(1, "A session"),
                    UiSession(1, "A session"),
                    UiSession(1, "A session"),
                    UiSession(1, "A session")
                )
            ),
            onEditSession = {},
            onNewSession = {},
            onDeleteSession = {},
            onSetSessionTitle = { _, _ -> },
            onStartSession = { _ -> }
        )
    }

}
