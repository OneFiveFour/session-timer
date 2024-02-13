package net.onefivefour.sessiontimer.feature.sessionplayer.api

import androidx.navigation.NavGraphBuilder
import de.onecode.navigator.api.Destination
import de.onecode.navigator.api.Parameter
import de.onecode.navigator.sessionPlayerScreen
import net.onefivefour.sessiontimer.feature.sessionplayer.NAV_ARG_SESSION_ID
import net.onefivefour.sessiontimer.feature.sessionplayer.SessionPlayerScreen

@Destination
@Parameter(name = NAV_ARG_SESSION_ID, type = Long::class)
object SessionPlayer

fun NavGraphBuilder.attachSessionPlayer() {
    sessionPlayerScreen {
        SessionPlayerScreen()
    }
}