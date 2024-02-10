package net.onefivefour.sessiontimer.navigation

import androidx.compose.runtime.Composable
import de.onecode.navigator.api.Destination
import de.onecode.navigator.Navigator



@Destination
object TestDestination

@Composable
fun AppNavGraph() {

    Navigator {




    }

//        sessionOverviewScreen {
//            SessionOverviewScreen(
//                onEditSession = { sessionId ->
//                    navigateToSessionEditor(sessionId)
//                }
//            )
//        }
//
//        sessionEditorScreen {
//            SessionEditorScreen { taskGroupId ->
//                navigateToTaskGroupEditor(taskGroupId)
//            }
//        }
//
//        taskGroupEditorScreen {
//            TaskGroupEditorScreen()
//        }
}
