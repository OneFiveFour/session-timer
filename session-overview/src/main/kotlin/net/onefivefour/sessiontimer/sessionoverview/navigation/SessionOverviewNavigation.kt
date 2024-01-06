package net.onefivefour.sessiontimer.sessionoverview.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import net.onefivefour.sessiontimer.sessionoverview.SessionOverviewScreen
import net.onefivefour.sessiontimer.sessionoverview.api.SessionOverviewNavigationApi

class SessionOverviewNavigation : SessionOverviewNavigationApi {

    override val sessionOverviewRoute = "session-overview"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(sessionOverviewRoute) {
            SessionOverviewScreen(
                modifier = modifier
            )
        }
    }
}