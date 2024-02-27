package net.onefivefour.sessiontimer.feature.sessionplayer.api

import androidx.navigation.NavGraphBuilder
import de.onecode.navigator.api.Destination
import de.onecode.navigator.api.Parameter
import de.onecode.navigator.sessionPlayerScreen
import javax.inject.Inject
import javax.inject.Singleton

const val NAV_ARG_SESSION_ID = "NAV_ARG_SESSION_ID"

@Destination
@Parameter(name = NAV_ARG_SESSION_ID, type = Long::class)
object SessionPlayer

fun NavGraphBuilder.attachSessionPlayer(
    sessionPlayerScreen: SessionPlayerScreen
) {
    sessionPlayerScreen {
        sessionPlayerScreen()
    }
}
