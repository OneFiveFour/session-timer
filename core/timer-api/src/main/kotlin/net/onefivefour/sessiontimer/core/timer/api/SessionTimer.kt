package net.onefivefour.sessiontimer.core.timer.api

import kotlin.time.Duration
import kotlinx.coroutines.flow.Flow
import net.onefivefour.sessiontimer.core.timer.api.model.TimerState

interface SessionTimer {
    val state: Flow<TimerState>
    fun init(totalDuration: Duration)
    fun start()
    fun pause()
    fun reset()
    fun seekTo(seekTo: Duration)
}
