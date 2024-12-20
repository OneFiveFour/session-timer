package net.onefivefour.sessiontimer.core.usecases.timer.test

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration
import kotlinx.coroutines.flow.MutableStateFlow
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.api.model.TimerState
import net.onefivefour.sessiontimer.core.usecases.api.timer.GetTimerStatusUseCase

@Singleton
class GetTimerStatusUseCaseFake @Inject constructor() : GetTimerStatusUseCase {

    private var currentTimerState = MutableStateFlow(
        TimerState(
            mode = TimerMode.IDLE,
            elapsedDuration = Duration.ZERO
        )
    )

    override fun execute() = currentTimerState

    suspend fun update(timerState: TimerState) {
        currentTimerState.emit(timerState)
    }
}
