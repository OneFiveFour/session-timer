package net.onefivefour.sessiontimer.core.timer

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
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Singleton
internal class SessionTimerImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : SessionTimer {

    private var timerStatus = MutableStateFlow(TimerStatus())

    private val timerScope = CoroutineScope(dispatcher)
    private var timerJob: Job? = null

    private var totalDuration: Duration? = null
    private var elapsedDuration = Duration.ZERO
    private var timerMode: TimerMode = TimerMode.IDLE

    private val frameRate = 1.seconds / 60

    override fun init(totalDuration: Duration) {
        this.totalDuration = totalDuration
    }

    override fun start() {
        checkForInit()
        this.timerMode = TimerMode.RUNNING

        if (timerJob == null || timerJob?.isActive == false) {
            timerJob = timerScope.launch {
                while (isActive) {
                    delay(frameRate)
                    elapsedDuration = elapsedDuration.plus(frameRate)
                    updateTimerStatus()
                }
            }
        }
    }

    override fun pause() {
        checkForInit()
        cancelTimer()
        timerMode = TimerMode.PAUSED
        updateTimerStatus()
    }

    override fun reset() {
        checkForInit()
        cancelTimer()
        timerMode = TimerMode.IDLE
        elapsedDuration = Duration.ZERO
        updateTimerStatus()
    }

    override fun seekTo(seekTo: Duration) {
        checkForInit()
        elapsedDuration = seekTo
        updateTimerStatus()
    }

    private fun updateTimerStatus() {
        totalDuration?.let { duration ->
            if (elapsedDuration >= duration) {
                cancelTimer()
                timerMode = TimerMode.FINISHED
            } else if (timerMode == TimerMode.FINISHED && elapsedDuration < duration){
                timerMode = TimerMode.PAUSED
            }

            println("update to $timerMode | $elapsedDuration")

            timerStatus.update {
                TimerStatus(
                    mode = timerMode,
                    elapsedDuration = elapsedDuration
                )
            }
        }

    }

    override fun getStatus(): Flow<TimerStatus> {
        return timerStatus.asStateFlow()
    }

    private fun cancelTimer() {
        timerJob?.cancel()
    }

    private fun checkForInit() {
        if (totalDuration == null) {
            throw NotInitializedException()
        }
    }
}
