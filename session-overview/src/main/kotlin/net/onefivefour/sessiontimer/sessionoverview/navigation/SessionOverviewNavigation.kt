package net.onefivefour.sessiontimer.sessionoverview.navigation

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_internal_builders_ViewModelComponentBuilder
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

            val viewModel: SessionOverviewViewModel = viewModel()
            val sessionOverviewState = viewModel.uiState.collectAsStateWithLifecycle()

            SessionOverviewScreen(
                sessionOverviewState.value
            ) { sessionId ->
                val sessionEditorRoute = sessionEditorNavigation.route
                navController.navigate(
                    route = "$sessionEditorRoute/$sessionId",

                )
            }
        }
    }
}