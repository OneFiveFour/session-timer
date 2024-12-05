package net.onefivefour.sessiontimer.feature.sessionplayer.domain

import net.onefivefour.sessiontimer.core.common.domain.model.Task
import kotlin.time.Duration

internal interface TaskOrchestrator {

    fun getCurrentTask() : Task?

    fun getNextTask(): Task?

    fun onCurrentTaskFinished()

    fun getDurationOfFinishedTasks(): Duration
}