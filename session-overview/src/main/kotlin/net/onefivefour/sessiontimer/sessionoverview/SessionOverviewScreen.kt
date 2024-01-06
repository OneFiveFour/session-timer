package net.onefivefour.sessiontimer.sessionoverview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.theme.typography
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SessionOverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: SessionOverviewViewModel = viewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {

        for (session in uiState.value.sessions) {
            Text(
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
    SessionOverviewScreen()
}