package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.compose.foundation.interaction.InteractionSource
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import org.junit.Test

class SessionItemIndicationTest {

    private val interactionSource: InteractionSource = mockk()

    @Test
    fun `WHEN create is called THEN SessionItemIndicationNode is returned`() {
        // WHEN
        val result = SessionItemIndication.create(interactionSource)

        // THEN
        assertThat(result).isInstanceOf(SessionItemIndicationNode::class.java)
    }
}