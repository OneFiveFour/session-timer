package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.ui.components.button.PrimaryButton
import net.onefivefour.sessiontimer.core.ui.components.dragger.Dragger
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

        PrimaryButton(
            text = stringResource(id = R.string.new_session),
            iconRes = UiR.drawable.ic_add,
            contentDescription = stringResource(id = R.string.new_session)
        ) {
            onNewSession()
        }
    }
}

@Composable
private fun SessionItem(
    session: UiSession,
    onEditSession: (Long) -> Unit,
    onStartSession: (Long) -> Unit
) {

    val cornerRadius = 8.dp

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Row(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = SessionItemIndication
            ) { onStartSession(session.id) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Dragger()

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            text = session.title,
            style = MaterialTheme.typography.titleLarge
        )

        Icon(
            modifier = Modifier
                .clip(RoundedCornerShape(cornerRadius))
                .clickable { onEditSession(session.id) }
                .padding(4.dp),
            painter = painterResource(id = UiR.drawable.ic_edit),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = stringResource(id = R.string.edit_session),
        )

    }
}


@Preview(showBackground = true)
@Composable
private fun SessionItemPreview() {
    SessionTimerTheme {
        SessionItem(
            session = UiSession(1, "A Session"),
            onEditSession = {}
        ) {}
    }
}

@Preview(showBackground = true)
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
