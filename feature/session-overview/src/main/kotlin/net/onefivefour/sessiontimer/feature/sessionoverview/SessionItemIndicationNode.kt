package net.onefivefour.sessiontimer.feature.sessionoverview

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class SessionItemIndicationNode(
    private val interactionSource: InteractionSource
) : Modifier.Node(), DrawModifierNode {

    private val cornerRadius = 12.dp

    private val animatedPercent = Animatable(0f)

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collectLatest { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> animateToPressed()
                    is PressInteraction.Release -> animateToResting()
                    is PressInteraction.Cancel -> animateToResting()
                }
            }
        }
    }

    private suspend fun animateToPressed() {
        animatedPercent.animateTo(
            targetValue = 1f,
            animationSpec = tween(200)
        )
    }

    private suspend fun animateToResting() {
        animatedPercent.animateTo(0f, spring())
    }

    override fun ContentDrawScope.draw() {
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
                paint = paint
            )

            canvas.drawRoundRect(
                left = size.width * 0.77f,
                top = rectPadding,
                right = size.width - rectPadding,
                bottom = size.height - rectPadding,
                radiusX = cornerRadiusPx,
                radiusY = cornerRadiusPx,
                paint = paint
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
