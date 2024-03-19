package net.onefivefour.sessiontimer.core.ui.components.button

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.IndicationInstance
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch

internal class ScaleIndicationInstance : IndicationInstance {

    var pressedAnimation: Job? = null
    var restingAnimation: Job? = null

    private val animatedPercent = Animatable(1f)

    private val gradientColorStart = Color(0xFFDFDEDE)
    private val gradientColorMid = Color(0xFFE7E7E7)
    private val gradientColorEnd = Color.Transparent
    private val gradientColors = listOf(
        gradientColorStart,
        gradientColorMid,
        gradientColorEnd
    )

    suspend fun animateToPressed(scope: CoroutineScope) {
        val currentPressedAnimation = pressedAnimation
        pressedAnimation = scope.launch {
            restingAnimation?.cancelAndJoin()
            currentPressedAnimation?.cancelAndJoin()
            animatedPercent.snapTo(1f)
            animatedPercent.animateTo(0f, spring())
        }

    }

    suspend fun animateToResting(scope: CoroutineScope) {
        restingAnimation = scope.launch {
            // Wait for the existing press animation to finish if it is still ongoing
            pressedAnimation?.join()
            animatedPercent.animateTo(1f, spring())
        }
    }

    private var radius: Float? = null
    private var baseOffsetX: Float? = null
    private var baseOffsetY: Float? = null

    private var offsetLeft: Offset? = null
    private var offsetRight: Offset? = null

    private var brushLeft: Brush? = null
    private var brushRight: Brush? = null

    override fun ContentDrawScope.drawIndication() {

        if (radius == null) {
            initVariables()
        }

        val animatedAlpha = 0.6f + (0.4f * animatedPercent.value)
        val animatedScaleXOuter = 4.dp.toPx() * animatedPercent.value
        val animatedTranslate = -animatedPercent.value * 2.dp.toPx()

        scale(
            scaleX = animatedScaleXOuter,
            scaleY = 1f
        ) {

            drawCircle(
                brush = brushLeft!!,
                radius = radius!!,
                center = offsetLeft!!,
                alpha = animatedAlpha
            )

            drawCircle(
                brush = brushRight!!,
                radius = radius!!,
                center = offsetRight!!,
                alpha = animatedAlpha
            )

            drawRoundRect(
                color = Color.White,
                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
            )

        }

        translate(
            top = animatedTranslate
        ) {
            this@drawIndication.drawContent()
        }
    }

    private fun ContentDrawScope.initVariables() {

        radius = size.minDimension / 1.8f
        baseOffsetX = size.width * 0.08f
        baseOffsetY = size.height / 2f

        offsetLeft = Offset(
            x = baseOffsetX!!,
            y = baseOffsetY!!
        )

        offsetRight = Offset(
            x = size.width - (baseOffsetX!!),
            y = baseOffsetY!!
        )

        brushLeft = Brush.radialGradient(
            colors = gradientColors,
            radius = radius!!,
            center = offsetLeft!!
        )

        brushRight = Brush.radialGradient(
            colors = gradientColors,
            radius = radius!!,
            center = offsetRight!!
        )

    }
}
