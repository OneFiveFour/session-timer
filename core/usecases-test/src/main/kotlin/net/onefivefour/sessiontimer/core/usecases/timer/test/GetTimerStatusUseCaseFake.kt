package net.onefivefour.sessiontimer.core.usecases.timer.test

import kotlinx.coroutines.flow.MutableStateFlow
import net.onefivefour.sessiontimer.core.timer.api.model.TimerMode
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus
import net.onefivefour.sessiontimer.core.usecases.api.timer.GetTimerStatusUseCase
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration

@Singleton
class GetTimerStatusUseCaseFake @Inject constructor() : GetTimerStatusUseCase {

    private var currentTimerStatus = MutableStateFlow(TimerStatus(
        mode = TimerMode.IDLE,
        elapsedDuration = Duration.ZERO
    ))

    override fun execute() = currentTimerStatus

    suspend fun update(timerStatus: TimerStatus) {
        currentTimerStatus.emit(timerStatus)
    }
}
