package net.onefivefour.sessiontimer.feature.taskgroupeditor.api

import androidx.navigation.NavGraphBuilder
import de.onecode.navigator.api.Destination
import de.onecode.navigator.api.Parameter
import de.onecode.navigator.taskGroupEditorScreen
import net.onefivefour.sessiontimer.feature.taskgroupeditor.NAV_ARG_TASK_GROUP_ID
import net.onefivefour.sessiontimer.feature.taskgroupeditor.TaskGroupEditorScreen

@Destination
@Parameter(name = NAV_ARG_TASK_GROUP_ID, type = Long::class)
object TaskGroupEditor

fun NavGraphBuilder.attachTaskGroupEditor() {
    taskGroupEditorScreen {
        TaskGroupEditorScreen()
    }
}