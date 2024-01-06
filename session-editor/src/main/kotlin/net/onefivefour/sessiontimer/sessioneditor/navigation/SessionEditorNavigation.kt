package net.onefivefour.sessiontimer.sessioneditor.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import net.onefivefour.sessiontimer.sessioneditor.SessionEditorScreen
import net.onefivefour.sessiontimer.sessioneditor.api.SessionEditorNavigationApi

class SessionEditorNavigation : SessionEditorNavigationApi {

    override val sessionEditorRoute = "session-editor"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(sessionEditorRoute) {
            SessionEditorScreen(
                modifier = modifier
            )
        }
    }
}