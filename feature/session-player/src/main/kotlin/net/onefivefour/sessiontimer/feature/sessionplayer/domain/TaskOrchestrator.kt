package net.onefivefour.sessiontimer.feature.sessionplayer.domain

import net.onefivefour.sessiontimer.core.common.domain.model.Task

interface TaskOrchestrator {
    fun getNextTask(): Task?
}