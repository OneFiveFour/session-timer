package net.onefivefour.sessiontimer.feature.sessionoverview

import androidx.navigation.NavGraphBuilder
import de.onecode.navigator.api.Destination
import de.onecode.navigator.api.Home
import de.onecode.navigator.api.Navigation
import de.onecode.navigator.api.Parameter
import de.onecode.navigator.sessionOverviewScreen

@Destination
//@Navigation(to = SessionEditor::class)
object SessionOverview

fun NavGraphBuilder.attachSessionOverview() {
    sessionOverviewScreen {
        SessionOverviewScreen(
            onEditSession = { sessionId ->
//                navigateToSessionEditor(sessionId)
            }
        )
    }
}