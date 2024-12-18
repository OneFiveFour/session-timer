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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.onefivefour.sessiontimer.core.theme.SessionTimerTheme
import net.onefivefour.sessiontimer.feature.sessionplayer.ui.modifier.arc


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
    startAngle: Float = 0f
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .glow(
                color = glowColor,
                width = glowWidth,
                radius = glowRadius,
                scaleFactor = glowScale,
                progress = progress,
                startAngle = startAngle
            )
            .arc(
                color = strokeColor,
                width = strokeWidth,
                progress = progress,
                startAngle = startAngle
            )

    )
}

private fun Modifier.glow(
    color: Color,
    width: Dp,
    radius: Dp,
    scaleFactor: Float,
    progress: Float,
    startAngle: Float
): Modifier = this.drawWithCache {

    val normalizedStartAngle = -90f + startAngle
    val arcDegrees = progress * 360f
    val bounds = RectF(0f, 0f, size.width, size.height)

    val glowingPaint = Paint().apply {
        asFrameworkPaint().also {
            it.isAntiAlias = true
            it.color = color.toArgb()
            it.strokeWidth =  width.toPx()
            it.style = android.graphics.Paint.Style.STROKE
            it.strokeJoin = android.graphics.Paint.Join.ROUND
            it.strokeCap = android.graphics.Paint.Cap.ROUND
            it.maskFilter = BlurMaskFilter(
                radius.toPx(),
                BlurMaskFilter.Blur.NORMAL
            )
        }
    }.asFrameworkPaint()

    val currentWidth = bounds.width()
    val scaledSize = currentWidth * scaleFactor
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
                normalizedStartAngle,
                arcDegrees,
                false,
                glowingPaint
            )
        }
    }
}


@Preview
@Composable
private fun NeumorphismButtonPreviewLight() {
    SessionTimerTheme {
        Surface {
            Box(
                Modifier
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
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun NeumorphismButtonPreviewDark() {
    SessionTimerTheme {
        Surface {
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
}