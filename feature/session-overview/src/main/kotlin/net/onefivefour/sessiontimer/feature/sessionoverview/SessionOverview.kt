package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import net.onefivefour.sessiontimer.core.ui.components.button.PrimaryButton
import net.onefivefour.sessiontimer.core.theme.typography
import net.onefivefour.sessiontimer.core.ui.R as UiR

@OptIn(ExperimentalFoundationApi::class)
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

    Column(modifier = Modifier.fillMaxSize()) {

        PrimaryButton(
            text = stringResource(id = R.string.new_session),
            iconRes = UiR.drawable.ic_add
        ) {
            onNewSession()
        }


        var editSessionId by remember { mutableStateOf<Long?>(null) }

        for (session in uiState.sessions) {

            Row {

                if (session.id == editSessionId) {

                    var currentText by remember { mutableStateOf(session.title) }

                    // Display a TextField in edit mode
                    var textFieldValue by remember {
                        mutableStateOf(
                            TextFieldValue(currentText, TextRange(currentText.length))
                        )
                    }

                    val focusRequester = remember { FocusRequester() }

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    TextField(
                        modifier = Modifier.focusRequester(focusRequester),
                        value = textFieldValue,
                        onValueChange = {
                            textFieldValue = it
                        }
                    )

                    Button(onClick = {
                        // Confirm the edited text when the button is pressed
                        editSessionId = null
                        currentText = textFieldValue.text
                        onSetSessionTitle(session.id, currentText)
                    }) {
                        Text(text = "OK")
                    }

                } else {

                    Text(
                        modifier = Modifier.combinedClickable(
                            onClick = { onEditSession(session.id) },
                            onLongClick = {
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

                Button(onClick = { onStartSession(session.id) }) {
                    Text(text = "Start")
                }


            }
        }
    }
}