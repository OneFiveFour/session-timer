package net.onefivefour.sessiontimer.feature.sessionplayer.ui.modifier

import android.graphics.RectF
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp

internal fun Modifier.arc(
    color: Color,
    width: Dp,
    progress: Float,
    startAngle: Float
): Modifier = this.drawWithCache {

    val normalizedStartAngle = -90f + startAngle
    val arcDegrees = progress * 360f
    val bounds = RectF(0f, 0f, size.width, size.height)

    onDrawBehind {
        drawArc(
            color = color,
            startAngle = normalizedStartAngle,
            sweepAngle = arcDegrees,
            useCenter = false,
            size = Size(bounds.width(), bounds.height()),
            topLeft = Offset(0f, 0f),
            style = Stroke(
                width = width.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}