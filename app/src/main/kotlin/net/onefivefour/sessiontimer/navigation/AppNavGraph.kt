package net.onefivefour.sessiontimer.navigation

import androidx.compose.runtime.Composable
import de.onecode.navigator.api.Destination
import de.onecode.navigator.api.Home
import de.onecode.navigator.api.Navigation
import de.onecode.navigator.api.Parameter
import io.redandroid.navigator.Navigator
import net.onefivefour.sessiontimer.sessioneditor.SessionEditorScreen
import net.onefivefour.sessiontimer.sessionoverview.SessionOverviewScreen

@Destination
@Parameter(name = "sessionId", type = Long::class)
object SessionEditor

@Destination
@Home
@Navigation(to = SessionEditor::class)
object SessionOverview

@Composable
fun AppNavGraph() {

    Navigator {

        sessionOverviewScreen {
            SessionOverviewScreen(
                onEditSession = { 
                    sessionId -> navigateToSessionEditor(sessionId) 
                }
            )
        }
        
        sessionEditorScreen { 
            SessionEditorScreen(sessionId = sessionId)
        }

    }
}
