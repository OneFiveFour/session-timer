package net.onefivefour.sessiontimer.navigation

import androidx.compose.runtime.Composable
import de.onecode.navigator.Navigator
import net.onefivefour.sessiontimer.feature.sessioneditor.api.attachSessionEditor
import net.onefivefour.sessiontimer.feature.sessionoverview.SessionOverviewScreen
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerScreen
import net.onefivefour.sessiontimer.feature.sessionplayer.api.attachSessionPlayer
import net.onefivefour.sessiontimer.feature.taskgroupeditor.api.attachTaskGroupEditor

@Composable
fun AppNavGraph(
    sessionPlayerScreen: SessionPlayerScreen
) {

    Navigator { navGraphBuilder ->

        sessionOverviewScreen {
            SessionOverviewScreen(
                onEditSession = { sessionId -> navigateToSessionEditor(sessionId) },
                onStartSession = { sessionId -> navigateToSessionPlayer(sessionId) }
            )
        }

        navGraphBuilder.attachSessionEditor()
        navGraphBuilder.attachTaskGroupEditor()
        navGraphBuilder.attachSessionPlayer(sessionPlayerScreen)
    }

}
