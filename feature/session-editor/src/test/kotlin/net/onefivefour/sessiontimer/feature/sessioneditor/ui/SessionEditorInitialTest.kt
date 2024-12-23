package net.onefivefour.sessiontimer.feature.sessioneditor.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SessionEditorInitialTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val initialMessage = "initial message"

    @Test
    fun errorMessageIsDisplayed() {
        with(composeTestRule) {
            initNode()

            onNodeWithText(initialMessage)
                .assertIsDisplayed()
        }
    }

    private fun ComposeContentTestRule.initNode() {
        setContent {
            SessionEditorInitial(initialMessage)
        }
    }
}
