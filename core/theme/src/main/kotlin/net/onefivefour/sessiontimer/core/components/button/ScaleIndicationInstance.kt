package net.onefivefour.sessiontimer.core.components.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.IndicationInstance
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp

internal class ScaleIndicationInstance(
    private val horizontalOffset: Float
) : IndicationInstance {

    private val animatedPercent = Animatable(1f)

    private val gradientColorStart = Color(0xFFA2A2A2)
    private val gradientColorMid = Color(0xFFD3D3D3)
    private val gradientColorEnd = Color.Transparent
    private val gradientColors = listOf(
        gradientColorStart,
        gradientColorMid,
        gradientColorEnd
    )

    suspend fun animateToPressed() {
        animatedPercent.animateTo(0f, spring())
    }

    suspend fun animateToResting() {
        animatedPercent.animateTo(1f, spring())
    }

    override fun ContentDrawScope.drawIndication() {

        val radius = size.minDimension / 1.8f
        val baseOffsetX = horizontalOffset / 1.4f
        val baseOffsetY = size.height / 2f

        val offsetLeft = Offset(
            x = baseOffsetX,
            y = baseOffsetY
        )

        val offsetRight = Offset(
            x = size.width - (baseOffsetX),
            y = baseOffsetY
        )

        val brushLeft = Brush.radialGradient(
            colors = gradientColors,
            radius = radius,
            center = offsetLeft
        )

        val brushRight = Brush.radialGradient(
            colors = gradientColors,
            radius = radius,
            center = offsetRight
        )

        val animatedAlpha = 0.6f + (0.4f * animatedPercent.value)
        val animatedScaleXOuter = 0.95f + (0.05f * animatedPercent.value)
        val animatedScaleXInner = 1.05f + (0.05f * -animatedPercent.value)
        val translate = -animatedPercent.value * 2.dp.toPx()

        scale(
            scaleX = animatedScaleXOuter,
            scaleY = 1f
        ) {

            drawCircle(
                brush = brushLeft,
                radius = radius,
                center = offsetLeft,
                alpha = animatedAlpha
            )

            drawCircle(
                brush = brushRight,
                radius = radius,
                center = offsetRight,
                alpha = animatedAlpha
            )

            translate(top = translate){
                scale(
                    scaleX = animatedScaleXInner,
                    scaleY = 1f
                ) {
                    this@drawIndication.drawContent()
                }
            }
        }
    }
}