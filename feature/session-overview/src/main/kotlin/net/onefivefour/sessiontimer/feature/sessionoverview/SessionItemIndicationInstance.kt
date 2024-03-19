package net.onefivefour.sessiontimer.feature.sessionoverview

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationInstance
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

internal class SessionItemIndicationInstance : IndicationInstance {

    private val cornerRadius = 12.dp

    private val animatedPercent = Animatable(0f)

    suspend fun animateToPressed() {
        animatedPercent.animateTo(
            targetValue = 1f,
            animationSpec = tween(200)
        )
    }

    suspend fun animateToResting() {
        animatedPercent.animateTo(0f, spring())
    }

    override fun ContentDrawScope.drawIndication() {

        val cornerRadiusPx = cornerRadius.toPx()
        val blurRadius = 6.dp.toPx()
        val paint = Paint().also {
            with(it.asFrameworkPaint()) {
                maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
                color = lerp(
                    Color(0xFFEFEFEF),
                    Color(0xFFFFFFFF),
                    animatedPercent.value
                ).toArgb()
            }
        }
        val rectPadding = 8.dp.toPx()

        drawIntoCanvas { canvas ->

            canvas.drawRoundRect(
                left = rectPadding,
                top = rectPadding,
                right = size.width * 0.27f,
                bottom = size.height - rectPadding,
                radiusX = cornerRadiusPx,
                radiusY = cornerRadiusPx,
                paint = paint,
            )

            canvas.drawRoundRect(
                left = size.width * 0.77f,
                top = rectPadding,
                right = size.width - rectPadding,
                bottom = size.height - rectPadding,
                radiusX = cornerRadiusPx,
                radiusY = cornerRadiusPx,
                paint = paint,
            )

        }

        val rectOffset = Offset(rectPadding, rectPadding)
        val rectHeight = this.size.height - (2 * rectPadding)
        val rectWidth = this.size.width - (2 * rectPadding)

        drawRoundRect(
            size = Size(rectWidth, rectHeight),
            topLeft = rectOffset,
            cornerRadius = CornerRadius(cornerRadiusPx),
            color = Color.White,
            alpha = 1f
        )

        drawContent()
    }
}