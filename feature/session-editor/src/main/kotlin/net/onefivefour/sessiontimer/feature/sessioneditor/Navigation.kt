package net.onefivefour.sessiontimer.feature.sessioneditor

import androidx.navigation.NavGraphBuilder
import de.onecode.navigator.api.Destination
import de.onecode.navigator.api.Navigation
import de.onecode.navigator.api.Parameter
import de.onecode.navigator.sessionEditorScreen

@Destination
@Parameter(name = "sessionId", type = Long::class)
//@Navigation(to = TaskGroupEditor::class)
object SessionEditor

fun NavGraphBuilder.attachSessionEditor() {
    sessionEditorScreen {
        SessionEditorScreen { taskGroupId ->
//            navigateToTaskGroupEditor(taskGroupId)
        }
    }
}