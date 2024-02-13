package net.onefivefour.sessiontimer.feature.taskgroupeditor.api

import androidx.navigation.NavGraphBuilder
import de.onecode.navigator.api.Destination
import de.onecode.navigator.api.Parameter
import de.onecode.navigator.taskGroupEditorScreen
import net.onefivefour.sessiontimer.feature.taskgroupeditor.TaskGroupEditorScreen


@Destination
@Parameter(name = "taskGroupId", type = Long::class)
object TaskGroupEditor

fun NavGraphBuilder.attachTaskGroupEditor() {
    taskGroupEditorScreen {
        TaskGroupEditorScreen()
    }
}