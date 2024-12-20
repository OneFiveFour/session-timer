package net.onefivefour.sessiontimer.core.timer.api

import kotlin.time.Duration
import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus

interface SessionTimer {
    fun init(totalDuration: Duration)
    fun start()
    fun pause()
    fun reset()
    fun getStatus(): Flow<TimerStatus>
    fun seekTo(seekTo: Duration)
}
