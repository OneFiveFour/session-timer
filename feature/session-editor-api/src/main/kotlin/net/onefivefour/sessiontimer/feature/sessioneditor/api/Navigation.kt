package net.onefivefour.sessiontimer.feature.sessioneditor.api

import androidx.navigation.NavGraphBuilder
import de.onecode.navigator.api.Destination
import de.onecode.navigator.api.Navigation
import de.onecode.navigator.api.Parameter
import de.onecode.navigator.sessionEditorScreen
import net.onefivefour.sessiontimer.feature.sessioneditor.NAV_ARG_SESSION_ID
import net.onefivefour.sessiontimer.feature.sessioneditor.SessionEditorScreen
import net.onefivefour.sessiontimer.feature.taskgroupeditor.api.TaskGroupEditor

@Destination
@Parameter(name = NAV_ARG_SESSION_ID, type = Long::class)
@Navigation(to = TaskGroupEditor::class)
object SessionEditor

fun NavGraphBuilder.attachSessionEditor() {
    sessionEditorScreen {
        SessionEditorScreen { taskGroupId ->
            navigateToTaskGroupEditor(taskGroupId)
        }
    }
}