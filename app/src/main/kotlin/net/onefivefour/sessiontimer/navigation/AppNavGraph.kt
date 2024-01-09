package net.onefivefour.sessiontimer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import net.onefivefour.sessiontimer.core.navigation.register
import net.onefivefour.sessiontimer.sessioneditor.navigation.SessionEditorNavigation
import net.onefivefour.sessiontimer.sessionoverview.navigation.SessionOverviewNavigation


@Composable
fun AppNavGraph(
    navController: NavHostController
) {

    val sessionEditor = SessionEditorNavigation()
    val sessionOverview = SessionOverviewNavigation(
        sessionEditor
    )

    NavHost(
        navController = navController,
        startDestination = sessionOverview.baseRoute
    ) {
        register(
            navigationApi = sessionOverview,
            navController = navController
        )

        register(
            navigationApi = sessionEditor,
            navController = navController
        )
    }
}
