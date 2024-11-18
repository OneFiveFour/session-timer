package net.onefivefour.sessiontimer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.onefivefour.sessiontimer.feature.sessioneditor.api.SessionEditor
import net.onefivefour.sessiontimer.feature.sessioneditor.ui.SessionEditorScreen
import net.onefivefour.sessiontimer.feature.sessionoverview.SessionOverviewScreen
import net.onefivefour.sessiontimer.feature.sessionoverview.api.SessionOverview
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayer
import net.onefivefour.sessiontimer.feature.sessionplayer.ui.SessionPlayerScreen
import net.onefivefour.sessiontimer.feature.taskgroupeditor.TaskGroupEditorScreen
import net.onefivefour.sessiontimer.feature.taskgroupeditor.api.TaskGroupEditor

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SessionOverview
    ) {
        composable<SessionOverview> {
            SessionOverviewScreen(
                onEditSession = { sessionId ->
                    val route = SessionEditor(sessionId)
                    navController.navigate(route)
                },
                onPlaySession = { sessionId ->
                    val route = SessionPlayer(sessionId)
                    navController.navigate(route)
                }
            )
        }

        composable<SessionEditor> {
            SessionEditorScreen(
                onEditTaskGroup = { taskGroupId ->
                    val route = TaskGroupEditor(taskGroupId)
                    navController.navigate(route)
                }
            )
        }

        composable<TaskGroupEditor> {
            TaskGroupEditorScreen()
        }

        composable<SessionPlayer> {
            SessionPlayerScreen()
        }
    }
}
