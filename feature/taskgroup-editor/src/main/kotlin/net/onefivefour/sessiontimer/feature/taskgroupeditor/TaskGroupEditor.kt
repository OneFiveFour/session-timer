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
import net.onefivefour.sessiontimer.core.common.domain.model.PlayMode

@Composable
internal fun TaskGroupEditor(uiState: UiState, onUpdateTaskGroup: (UiTaskGroup) -> Unit) {
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
    var numberOfRandomTasks by remember { mutableIntStateOf(taskGroup.numberOfRandomTasks) }

    Column {
        TextField(
            value = title,
            onValueChange = { title = it }
        )

        Row {
            val color1 = Color(132, 228, 253, 255)
            val color2 = Color(250, 225, 178, 255)
            val color3 = Color(181, 232, 196, 255)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color1)
                    .clickable { color = color1 }
            ) {
                if (color == color1) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(Color.Black)
                            .align(Alignment.Center)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color2)
                    .clickable { color = color2 }
            ) {
                if (color == color2) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(Color.Black)
                            .align(Alignment.Center)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color3)
                    .clickable { color = color3 }
            ) {
                if (color == color3) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(Color.Black)
                            .align(Alignment.Center)
                    )
                }
            }
        }

        Button(onClick = {
            playMode = when (playMode) {
                PlayMode.SEQUENCE -> PlayMode.RANDOM_ALL_TASKS
                PlayMode.RANDOM_SINGLE_TASK -> PlayMode.SEQUENCE
                PlayMode.RANDOM_N_TASKS -> PlayMode.RANDOM_SINGLE_TASK
                PlayMode.RANDOM_ALL_TASKS -> PlayMode.RANDOM_N_TASKS
            }
        }) {
            Text(text = playMode.toString())
        }

        TextField(
            value = numberOfRandomTasks.toString(),
            onValueChange = { numberOfRandomTasks = it.toInt() }
        )

        Button(onClick = {
            val updatedTaskGroup = UiTaskGroup(
                taskGroup.id,
                title,
                color,
                playMode,
                numberOfRandomTasks,
                taskGroup.tasks
            )
            onUpdateTaskGroup(updatedTaskGroup)
        }) {
            Text(text = "OK")
        }
    }
}
