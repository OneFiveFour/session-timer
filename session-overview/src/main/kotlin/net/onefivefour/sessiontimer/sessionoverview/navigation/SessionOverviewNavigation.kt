package net.onefivefour.sessiontimer.sessionoverview.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import net.onefivefour.sessiontimer.sessioneditor.api.SessionEditorNavigationApi
import net.onefivefour.sessiontimer.sessionoverview.SessionOverviewScreen
import net.onefivefour.sessiontimer.sessionoverview.SessionOverviewViewModel
import net.onefivefour.sessiontimer.sessionoverview.api.SessionOverviewNavigationApi
import javax.inject.Inject

class SessionOverviewNavigation @Inject constructor(
    private val sessionEditorNavigation: SessionEditorNavigationApi
) : SessionOverviewNavigationApi {

    override val route = "session-overview"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController
    ) {
        navGraphBuilder.composable(route) {

            val viewModel: SessionOverviewViewModel = hiltViewModel()
            val sessionOverviewState = viewModel.uiState.collectAsStateWithLifecycle()

            SessionOverviewScreen(
                sessionOverviewState.value
            ) { sessionId ->
                val sessionEditorRoute = sessionEditorNavigation.baseRoute
                navController.navigate(
                    route = "$sessionEditorRoute/$sessionId",

                )
            }
        }
    }
}