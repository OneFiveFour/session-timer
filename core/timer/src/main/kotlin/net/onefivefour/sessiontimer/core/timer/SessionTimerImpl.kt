package net.onefivefour.sessiontimer.core.timer

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
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

@ViewModelScoped
internal class SessionTimerImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : SessionTimer {

    private var timerStatus = MutableStateFlow(TimerStatus())

    private val timerScope = CoroutineScope(dispatcher)
    private var timerJob: Job? = null

    private var totalDuration = Duration.ZERO
    private var elapsedDuration = Duration.ZERO
    private var timerMode: TimerMode = TimerMode.IDLE

    override fun start(totalDuration: Duration) {
        this.totalDuration = totalDuration
        this.timerMode = TimerMode.RUNNING

        if (timerJob == null || timerJob?.isActive == false) {
            timerJob = timerScope.launch {
                while (isActive) {
                    delay(1_000)
                    elapsedDuration = elapsedDuration.plus(1.seconds)
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
        elapsedDuration = Duration.ZERO
        updateTimerStatus()
    }

    private fun updateTimerStatus() {
        if (elapsedDuration >= totalDuration) {
            cancelTimer()
            timerMode = TimerMode.FINISHED
        }

        timerStatus.update {
            TimerStatus(
                mode = timerMode,
                elapsedDuration = elapsedDuration
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
