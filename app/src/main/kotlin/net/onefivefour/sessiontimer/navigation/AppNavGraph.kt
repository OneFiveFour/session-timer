package net.onefivefour.sessiontimer.navigation

import androidx.compose.runtime.Composable
import de.onecode.navigator.Navigator
import net.onefivefour.sessiontimer.feature.sessioneditor.attachSessionEditor
import net.onefivefour.sessiontimer.feature.sessionoverview.SessionOverviewScreen
import net.onefivefour.sessiontimer.feature.taskgroupeditor.attachTaskGroupEditor

@Composable
fun AppNavGraph() {

    Navigator { navGraphBuilder ->

        sessionOverviewScreen {
            SessionOverviewScreen(
                onEditSession = { sessionId ->
//                    navigateToSessionEditor(sessionId)
                }
            )
        }

        navGraphBuilder.attachSessionEditor()
        navGraphBuilder.attachTaskGroupEditor()
    }

}
