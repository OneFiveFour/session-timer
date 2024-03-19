package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.core.theme.typography
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

    val gradientColorStart = Color(0xFF838383)
    val gradientColorEnd = Color.Transparent
    val gradientColors = listOf(
        gradientColorStart,
        gradientColorEnd
    )



    Box(
        modifier = Modifier.drawBehind {

            val radius = this.size.minDimension / 2.4f
            val offsetXLeft = radius * 1f
            val offsetXRight = this.size.width - (radius * 1.2f)
            val offsetY = this.size.height / 2f

            val offsetLeft = Offset(offsetXLeft, offsetY)
            val offsetRight = Offset(offsetXRight, offsetY)

            val brushLeft = Brush.radialGradient(
                colors = gradientColors,
                radius = radius,
                center = offsetLeft
            )
            val brushRight = Brush.radialGradient(
                colors = gradientColors,
                radius = radius,
                center = offsetRight
            )

            drawCircle(
                brush = brushLeft,
                radius = radius,
                center = offsetLeft
            )

            drawCircle(
                color = Color.Blue,
                radius = radius,
                center = offsetRight
            )

        }) {

        Row(
            Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Green),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Dragger()

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface,
                text = session.title,
                style = typography.titleLarge
            )

            Icon(
                modifier = Modifier
                    .background(Color.Red)
                    .alpha(0.5f),
                painter = painterResource(id = UiR.drawable.ic_edit),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(id = R.string.edit_session),
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SessionItemPreview() {
    SessionTimerTheme {
        SessionItem(
            session = UiSession(1, "A Session"),
            onEditSession = {},
            onStartSession = {}
        )
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
