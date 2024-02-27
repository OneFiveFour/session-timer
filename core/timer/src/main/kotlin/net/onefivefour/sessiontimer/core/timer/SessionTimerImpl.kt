package net.onefivefour.sessiontimer.core.timer

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.di.DefaultDispatcher
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration

internal class SessionTimerImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : SessionTimer {

    private var timerStatus = MutableStateFlow(TimerStatus())

    private val timerScope = CoroutineScope(dispatcher)
    private var timerJob: Job? = null

    private var totalSeconds = 0L
    private var elapsedSeconds: Int = 0
    private var timerMode: TimerMode = TimerMode.IDLE

    override fun start(totalDuration: Duration) {

        this.totalSeconds = totalDuration.inWholeSeconds
        this.timerMode = TimerMode.RUNNING

        if (timerJob == null || timerJob?.isActive == false) {
            timerJob = timerScope.launch {
                while (isActive) {
                    delay(1000)
                    elapsedSeconds++
                    updateTimerStatus()
                }
            }
        }
    }

    override fun pause() {
        cancelTimer()
        timerMode = TimerMode.PAUSED
        updateTimerStatus()
    }

    override fun reset() {
        cancelTimer()
        timerMode = TimerMode.IDLE
        elapsedSeconds = 0
        updateTimerStatus()
    }

    private fun updateTimerStatus() {

        if (elapsedSeconds >= totalSeconds) {
            cancelTimer()
            timerMode = TimerMode.FINISHED
        }

        timerStatus.update {
            TimerStatus(
                mode = timerMode,
                elapsedSeconds = elapsedSeconds
            )
        }
    }

    private fun cancelTimer() {
        timerJob?.cancel()
    }

    override fun getStatus(): Flow<TimerStatus> {
        return timerStatus.asStateFlow()
    }
}