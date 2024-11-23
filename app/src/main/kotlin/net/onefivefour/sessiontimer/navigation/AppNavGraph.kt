package net.onefivefour.sessiontimer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.onefivefour.sessiontimer.feature.sessioneditor.api.SessionEditorRoute
import net.onefivefour.sessiontimer.feature.sessioneditor.ui.SessionEditorScreen
import net.onefivefour.sessiontimer.feature.sessionoverview.SessionOverviewScreen
import net.onefivefour.sessiontimer.feature.sessionoverview.api.SessionOverviewRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.ui.SessionPlayerScreen
import net.onefivefour.sessiontimer.feature.taskgroupeditor.TaskGroupEditorScreen
import net.onefivefour.sessiontimer.feature.taskgroupeditor.api.TaskGroupEditorRoute

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SessionOverviewRoute
    ) {
        composable<SessionOverviewRoute> {
            SessionOverviewScreen(
                onEditSession = { sessionId ->
                    val route = SessionEditorRoute(sessionId)
                    navController.navigate(route)
                },
                onPlaySession = { sessionId ->
                    val route = SessionPlayerRoute(sessionId)
                    navController.navigate(route)
                }
            )
        }

        composable<SessionEditorRoute> {
            SessionEditorScreen(
                onEditTaskGroup = { taskGroupId ->
                    val route = TaskGroupEditorRoute(taskGroupId)
                    navController.navigate(route)
                }
            )
        }

        composable<TaskGroupEditorRoute> {
            TaskGroupEditorScreen()
        }

        composable<SessionPlayerRoute> {
            SessionPlayerScreen()
        }
    }
}
