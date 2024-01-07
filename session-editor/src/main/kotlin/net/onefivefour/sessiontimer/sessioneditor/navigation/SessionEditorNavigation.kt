package net.onefivefour.sessiontimer.sessioneditor.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import net.onefivefour.sessiontimer.sessioneditor.SessionEditorScreen
import net.onefivefour.sessiontimer.sessioneditor.api.SessionEditorNavigationApi

class SessionEditorNavigation : SessionEditorNavigationApi {

    override val route = "session-editor/{$KEY_SESSION_ID}"

    companion object {
        private const val KEY_SESSION_ID = "KEY_SESSION_ID"
    }

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.composable(
            route = route,
            arguments = listOf(
                navArgument(KEY_SESSION_ID) { type = NavType.LongType }
            )
        ) { backStackEntry ->

            val arguments = requireNotNull(backStackEntry.arguments)
            val sessionId = arguments.getLong(KEY_SESSION_ID)

            SessionEditorScreen(
                sessionId = sessionId
            )
        }
    }
}