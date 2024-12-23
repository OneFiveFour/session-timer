package net.onefivefour.sessiontimer.feature.sessionplayer.ui.modifier

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

internal fun Modifier.addSessionControls(): Modifier = this.drawBehind {
    val cornerRadiusPx = 16.dp.toPx()
    val blurRadius = 20.dp.toPx()
    val rectPadding = 6.dp.toPx()

    drawIntoCanvas { canvas ->

        val paint = Paint().also {
            with(it.asFrameworkPaint()) {
                maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
                color = Color(0xFFE0E0E0).toArgb()
            }
        }

        canvas.drawRoundRect(
            left = rectPadding + 2.dp.toPx(),
            top = rectPadding + 6.dp.toPx(),
            right = size.width * 0.15f,
            bottom = size.height - rectPadding - 6.dp.toPx(),
            radiusX = cornerRadiusPx,
            radiusY = cornerRadiusPx,
            paint = paint
        )

        canvas.drawRoundRect(
            left = size.width * 0.85f,
            top = rectPadding + 6.dp.toPx(),
            right = size.width - rectPadding - 2.dp.toPx(),
            bottom = size.height - rectPadding - 6.dp.toPx(),
            radiusX = cornerRadiusPx,
            radiusY = cornerRadiusPx,
            paint = paint
        )
    }

    val rectOffset = Offset(
        x = rectPadding,
        y = rectPadding
    )
    val rectHeight = this.size.height - (2 * rectPadding)
    val rectWidth = this.size.width - (2 * rectPadding)

    drawRoundRect(
        size = Size(rectWidth, rectHeight),
        topLeft = rectOffset,
        cornerRadius = CornerRadius(cornerRadiusPx),
        color = Color.White,
        alpha = 1f
    )
}
