package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TaskGroupEditorScreen() {

    val viewModel: TaskGroupEditorViewModel = hiltViewModel()
    val taskGroupEditorState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskGroupEditor(
        uiState = taskGroupEditorState,
        onSetTaskGroupTitle = { taskId, title -> viewModel.setTaskGroupTitle(taskId, title) },
        onSetTaskGroupColor = { taskId, color -> viewModel.setTaskGroupColor(taskId, color) }
    )
}


@Composable
internal fun TaskGroupEditor(
    uiState: UiState,
    onSetTaskGroupTitle: (Long, String) -> Unit,
    onSetTaskGroupColor: (Long, Color) -> Unit
) {

    when (uiState) {
        UiState.Initial -> {
            Text(text = "TaskGroup Editor")
            return
        }

        is UiState.Success -> {
            checkNotNull(uiState.taskGroup)
        }
    }

    val taskGroup = uiState.taskGroup

    var taskGroupTitle by remember {
        mutableStateOf(taskGroup.title)
    }

    var taskGroupColor by remember {
        mutableStateOf(taskGroup.color)
    }

    Column {
        TextField(
            value = taskGroupTitle,
            onValueChange = { taskGroupTitle = it }
        )

        Row {
            val color1 = Color(100, 200, 50)
            val color2 = Color(200, 100, 50)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color1)
                    .clickable { taskGroupColor = color1 }
            ) {
                if (taskGroupColor == color1) {
                    Box(modifier = Modifier.size(10.dp).background(Color.Black).align(Alignment.Center))
                }
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color2)
                    .clickable { taskGroupColor = color2 }
            ) {
                if (taskGroupColor == color2) {
                    Box(modifier = Modifier.size(10.dp).background(Color.Black).align(Alignment.Center))
                }
            }
        }

        Button(onClick = {
            onSetTaskGroupTitle(taskGroup.id, taskGroupTitle)
            onSetTaskGroupColor(taskGroup.id, taskGroupColor)
        }) {
            Text(text = "OK")
        }
    }


}
