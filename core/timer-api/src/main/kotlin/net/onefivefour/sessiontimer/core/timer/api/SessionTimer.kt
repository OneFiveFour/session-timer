package net.onefivefour.sessiontimer.core.timer.api

import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus

interface SessionTimer {
    fun start(totalSeconds: Int)
    fun pause()
    fun reset()
    fun getStatus() : Flow<net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus>
}