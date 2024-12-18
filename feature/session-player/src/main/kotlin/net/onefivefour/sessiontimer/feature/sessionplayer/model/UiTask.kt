package net.onefivefour.sessiontimer.feature.sessionplayer.model

import androidx.compose.ui.graphics.Color
import kotlin.time.Duration

data class UiTask(
    val id: Long,
    val taskGroupTitle: String,
    val taskGroupColor: Color,
    val taskTitle: String,
    val taskDuration: Duration
)
