package net.onefivefour.sessiontimer.core.timer

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.di.DefaultDispatcher
import net.onefivefour.sessiontimer.core.timer.api.SessionTimer
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.api.model.TimerState
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Singleton
internal class SessionTimerImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : SessionTimer {

    private val frameRate = 1.seconds / 60
    private val timerScope = CoroutineScope(dispatcher)

    private var _state = MutableStateFlow(TimerState())
    private var timerJob: Job? = null

    override fun init(totalDuration: Duration) {
        _state.update { it.copy(totalDuration = totalDuration) }
    }

    override fun start() {
        requireInit()
        if (timerJob?.isActive == true) return

        _state.update { it.copy(mode = TimerMode.RUNNING) }
        timerJob = timerScope.launch {
            while (isActive) {
                delay(frameRate)
                updateProgress(frameRate)
            }
        }
    }

    override fun pause() {
        requireInit()
        timerJob?.cancel()
        _state.update { it.copy(mode = TimerMode.PAUSED) }
    }

    override fun reset() {
        requireInit()
        cancelTimer()
        _state.update {
            it.copy(
                elapsedDuration = Duration.ZERO,
                mode = TimerMode.IDLE
            )
        }
    }

    override fun seekTo(seekTo: Duration) {
        requireInit()
        _state.update { it.copy(elapsedDuration = seekTo) }
        updateTimerStatus()
    }

    private fun updateProgress(increment: Duration) {
        _state.update { state ->
            val newElapsed = state.elapsedDuration + increment
            val currentTotalDuration = state.totalDuration ?: Duration.ZERO
            state.copy(
                elapsedDuration = newElapsed,
                mode = when {
                    newElapsed >= currentTotalDuration -> {
                        cancelTimer()
                        TimerMode.FINISHED
                    }

                    state.mode == TimerMode.FINISHED &&
                            newElapsed < currentTotalDuration -> TimerMode.PAUSED

                    else -> state.mode
                }
            )
        }
    }

    private fun updateTimerStatus() = updateProgress(Duration.ZERO)

    override fun getStatus() = _state.stateIn(
        scope = timerScope,
        started = SharingStarted.Eagerly,
        initialValue = TimerState()
    )

    private fun cancelTimer() {
        timerJob?.cancel()
    }

    private fun requireInit() {
        if (_state.value.totalDuration == null) {
            throw NotInitializedException()
        }
    }
}
