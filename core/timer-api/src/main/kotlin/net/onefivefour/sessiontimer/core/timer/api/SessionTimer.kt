package net.onefivefour.sessiontimer.core.timer.api

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface SessionTimer {
    fun start(totalDuration: Duration)
    fun pause()
    fun reset()
    fun getStatus() : Flow<net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus>
}