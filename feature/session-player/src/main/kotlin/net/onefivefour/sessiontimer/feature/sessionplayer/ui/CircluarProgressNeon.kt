package net.onefivefour.sessiontimer.feature.sessionplayer.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.BlurMaskFilter
import android.graphics.RectF
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme


@Composable
fun CircluarProgressNeon(
    modifier: Modifier = Modifier,
    strokeColor: Color,
    strokeWidth: Dp,
    glowColor: Color = strokeColor.copy(alpha = 0.2f),
    glowWidth: Dp,
    glowRadius: Dp,
    glowScale: Float = 1f,
    progress: Float = 1f,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .neonBorder(
                strokeColor = strokeColor,
                strokeWidthDp = strokeWidth,
                glowColor = glowColor,
                glowWidthDp = glowWidth,
                glowRadiusDp = glowRadius,
                glowScale = glowScale,
                progress = progress
            )

    )
}


fun Modifier.neonBorder(
    strokeColor: Color,
    strokeWidthDp: Dp,
    glowColor: Color,
    glowWidthDp: Dp,
    glowRadiusDp: Dp,
    glowScale: Float = 1f,
    progress: Float = 1f,
): Modifier = this.drawWithCache {

    val strokeWidthPx = strokeWidthDp.toPx()
    val glowWidthPx = glowWidthDp.toPx()
    val glowRadiusPx = glowRadiusDp.toPx()
    val arcDegrees = progress * 360f
    val bounds = RectF(0f, 0f, size.width, size.height)

    val startAngle = -90f

    // Applying a blur effect only to the outer glowing arc is not possible using Compose's Brush.
    // To achieve this, we use the native Paint class with a BlurMaskFilter and draw the arc using the
    // native Android Canvas to handle the blur for the glowing effect.
    val glowingPaint = Paint().apply {
        asFrameworkPaint().apply {
            isAntiAlias = true
            color = glowColor.toArgb()
            strokeWidth = glowWidthPx
            style = android.graphics.Paint.Style.STROKE
            strokeJoin = android.graphics.Paint.Join.ROUND
            strokeCap = android.graphics.Paint.Cap.ROUND
            maskFilter = BlurMaskFilter(
                glowRadiusPx,
                BlurMaskFilter.Blur.NORMAL
            )
        }
    }.asFrameworkPaint()

    val currentWidth = bounds.width()
    val scaledSize = currentWidth * glowScale
    val sizeOffset = (scaledSize - currentWidth) / 2
    val glowBounds = RectF(
        0 - sizeOffset,
        0 - sizeOffset,
        currentWidth + sizeOffset,
        currentWidth + sizeOffset
    )

    onDrawBehind {
        //draw with the help of native canvas blurred and sweep-gradient  glowing ark
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawArc(
                glowBounds,
                startAngle,
                arcDegrees,
                false,
                glowingPaint
            )
        }

        //draw with the help of native canvas sweep-gradient body ark
        drawArc(
            color = strokeColor,
            startAngle = startAngle,
            sweepAngle = arcDegrees,
            useCenter = false,
            size = Size(bounds.width(), bounds.height()),
            topLeft = Offset(0f, 0f),
            style = Stroke(
                width = strokeWidthPx,
                cap = StrokeCap.Round
            )
        )
    }
}


@Preview
@Composable
private fun NeumorphismButtonPreviewLight() {
    SessionTimerTheme {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .size(150.dp)
                .padding(16.dp)
        ) {
            CircluarProgressNeon(
                strokeColor = MaterialTheme.colorScheme.background,
                strokeWidth = 3.dp,
                glowColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                glowWidth = 14.dp,
                glowRadius = 10.dp
            )
            CircluarProgressNeon(
                strokeColor = MaterialTheme.colorScheme.primary,
                strokeWidth = 6.dp,
                glowWidth = 10.dp,
                glowRadius = 6.dp,
                glowScale = 1.03f,
                progress = 0.4f
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun NeumorphismButtonPreviewDark() {
    SessionTimerTheme {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .size(150.dp)
                .padding(16.dp)
        ) {
            CircluarProgressNeon(
                strokeColor = MaterialTheme.colorScheme.background,
                strokeWidth = 3.dp,
                glowColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                glowWidth = 14.dp,
                glowRadius = 10.dp
            )
            CircluarProgressNeon(
                strokeColor = MaterialTheme.colorScheme.primary,
                strokeWidth = 6.dp,
                glowWidth = 10.dp,
                glowRadius = 6.dp,
                glowScale = 1.03f,
                progress = 0.8f
            )
        }
    }
}