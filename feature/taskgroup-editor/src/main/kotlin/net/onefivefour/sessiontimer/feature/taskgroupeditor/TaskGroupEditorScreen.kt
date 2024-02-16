package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode

@Composable
fun TaskGroupEditorScreen() {

    val viewModel: TaskGroupEditorViewModel = hiltViewModel()
    val taskGroupEditorState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskGroupEditor(
        uiState = taskGroupEditorState,
        onSetTaskGroupTitle = { taskId, title -> viewModel.setTaskGroupTitle(taskId, title) },
        onSetTaskGroupColor = { taskId, color -> viewModel.setTaskGroupColor(taskId, color) },
        onSetTaskGroupPlayMode = { taskId, playMode -> viewModel.setTaskGroupPlayMode(taskId, playMode) },
        onSetTaskGroupNumberOfRandomTasks = { taskId, number -> viewModel.setTaskGroupNumberOfRandomTasks(taskId, number) },
    )
}


@Composable
internal fun TaskGroupEditor(
    uiState: UiState,
    onSetTaskGroupTitle: (Long, String) -> Unit,
    onSetTaskGroupColor: (Long, Color) -> Unit,
    onSetTaskGroupPlayMode: (Long, PlayMode) -> Unit,
    onSetTaskGroupNumberOfRandomTasks: (Long, Int) -> Unit
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

    var title by remember { mutableStateOf(taskGroup.title) }
    var color by remember { mutableStateOf(taskGroup.color) }
    var playMode by remember { mutableStateOf(taskGroup.playMode) }
    var numberOfRandomTasks by remember { mutableStateOf(taskGroup.numberOfRandomTasks) }

    Column {
        TextField(
            value = title,
            onValueChange = { title = it }
        )

        Row {
            val color1 = Color(100, 200, 50)
            val color2 = Color(200, 100, 50)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color1)
                    .clickable { color = color1 }
            ) {
                if (color == color1) {
                    Box(modifier = Modifier
                        .size(10.dp)
                        .background(Color.Black)
                        .align(Alignment.Center))
                }
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color2)
                    .clickable { color = color2 }
            ) {
                if (color == color2) {
                    Box(modifier = Modifier
                        .size(10.dp)
                        .background(Color.Black)
                        .align(Alignment.Center))
                }
            }
        }

        Button(onClick = {
            playMode = when (playMode) {
                PlayMode.SEQUENCE -> PlayMode.RANDOM
                PlayMode.RANDOM -> PlayMode.SEQUENCE
            }
        }) {
            Text(text = playMode.toString())
        }

        TextField(
            value = numberOfRandomTasks.toString(),
            onValueChange = { numberOfRandomTasks = it.toInt() }
        )

        Button(onClick = {
            onSetTaskGroupTitle(taskGroup.id, title)
            onSetTaskGroupColor(taskGroup.id, color)
            onSetTaskGroupPlayMode(taskGroup.id, playMode)
            onSetTaskGroupNumberOfRandomTasks(taskGroup.id, numberOfRandomTasks)
        }) {
            Text(text = "OK")
        }
    }


}
