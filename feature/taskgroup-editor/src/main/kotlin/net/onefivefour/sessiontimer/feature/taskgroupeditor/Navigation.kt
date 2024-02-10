package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.navigation.NavGraphBuilder
import de.onecode.navigator.api.Destination
import de.onecode.navigator.api.Parameter
import de.onecode.navigator.taskGroupEditorScreen


// TODO create constants for param names in each module
@Destination
@Parameter(name = "taskGroupId", type = Long::class)
object TaskGroupEditor

fun NavGraphBuilder.attachTaskGroupEditor() {
    taskGroupEditorScreen {
        TaskGroupEditorScreen()
    }
}