package net.onefivefour.sessiontimer.navigation

import androidx.compose.runtime.Composable
import de.onecode.navigator.Navigator
import de.onecode.navigator.api.Destination
import de.onecode.navigator.api.Home
import de.onecode.navigator.api.Navigation
import de.onecode.navigator.api.Parameter
import net.onefivefour.sessiontimer.sessioneditor.SessionEditorScreen
import net.onefivefour.sessiontimer.sessionoverview.SessionOverviewScreen
import net.onefivefour.sessiontimer.taskgroupeditor.TaskGroupEditorScreen

@Destination
@Parameter(name = "sessionId", type = Long::class)
@Navigation(to = TaskGroupEditor::class)
object SessionEditor

@Destination
@Home
@Navigation(to = SessionEditor::class)
object SessionOverview

// TODO create constants for param names in each module
@Destination
@Parameter(name = "taskGroupId", type = Long::class)
object TaskGroupEditor


@Composable
fun AppNavGraph() {

    Navigator {

        sessionOverviewScreen {
            SessionOverviewScreen(
                onEditSession = { sessionId ->
                    navigateToSessionEditor(sessionId)
                }
            )
        }

        sessionEditorScreen {
            SessionEditorScreen { taskGroupId ->
                navigateToTaskGroupEditor(taskGroupId)
            }
        }

        taskGroupEditorScreen {
            TaskGroupEditorScreen()
        }
    }
}
