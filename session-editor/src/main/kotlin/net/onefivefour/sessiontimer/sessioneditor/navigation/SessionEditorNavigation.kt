package net.onefivefour.sessiontimer.sessioneditor.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import net.onefivefour.sessiontimer.sessioneditor.SessionEditorScreen
import net.onefivefour.sessiontimer.sessioneditor.SessionEditorViewModel
import net.onefivefour.sessiontimer.sessioneditor.api.SessionEditorNavigationApi

class SessionEditorNavigation : SessionEditorNavigationApi {

    override val baseRoute = "session-editor"

    companion object {
        const val KEY_SESSION_ID = "KEY_SESSION_ID"
    }

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.composable(
            route = "$baseRoute/{${KEY_SESSION_ID}}",
            arguments = listOf(
                navArgument(KEY_SESSION_ID) { type = NavType.LongType }
            )
        ) {

            val viewModel: SessionEditorViewModel = hiltViewModel()
            val sessionEditorState = viewModel.uiState.collectAsStateWithLifecycle()

            SessionEditorScreen(
                uiState = sessionEditorState.value
            )
        }
    }
}