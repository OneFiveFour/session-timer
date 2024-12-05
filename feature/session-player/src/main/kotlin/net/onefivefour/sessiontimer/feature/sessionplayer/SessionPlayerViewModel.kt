package net.onefivefour.sessiontimer.feature.sessionplayer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.timer.api.model.TimerStatus
import net.onefivefour.sessiontimer.core.usecases.session.GetSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.GetTimerStatusUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.PauseTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.ResetTimerUseCase
import net.onefivefour.sessiontimer.core.usecases.timer.StartTimerUseCase
import net.onefivefour.sessiontimer.feature.sessionplayer.api.SessionPlayerRoute
import net.onefivefour.sessiontimer.feature.sessionplayer.domain.SessionCompiler
import net.onefivefour.sessiontimer.feature.sessionplayer.domain.TaskOrchestrator
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiCompiledSession
import net.onefivefour.sessiontimer.feature.sessionplayer.model.UiState
import net.onefivefour.sessiontimer.feature.sessionplayer.model.toUiSession
import net.onefivefour.sessiontimer.feature.sessionplayer.model.toUiTask
import kotlin.concurrent.timer
import kotlin.time.Duration

@HiltViewModel
internal class SessionPlayerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getSessionUseCase: GetSessionUseCase,
    getTimerStatusUseCase: GetTimerStatusUseCase,
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val resetTimerUseCase: ResetTimerUseCase
) : ViewModel() {

    private val sessionId = savedStateHandle.toRoute<SessionPlayerRoute>().sessionId

    private lateinit var compiledSession: UiCompiledSession

    private var currentTaskIndex = 0

    private var _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {

        viewModelScope.launch {
            combine(
                getSessionUseCase.execute(sessionId),
                getTimerStatusUseCase.execute()
            ) { session, timerStatus ->

                if (session == null) {
                    return@combine UiState.Initial
                }

                compiledSession = SessionCompiler.compile(session)

                when {
                    compiledSession.taskList.isEmpty() -> UiState.Error("Session has no tasks")
                    else -> computeUiState(compiledSession, timerStatus)
                }
            }.collect { newUiState ->
                _uiState.update { newUiState }
            }
        }
    }

    private fun computeUiState(
        compiledSession: UiCompiledSession,
        timerStatus: TimerStatus
    ): UiState {

        val elapsedDuration = timerStatus.elapsedDuration
        val pastTaskDuration = compiledSession.taskList
            .take(currentTaskIndex)
            .map { it.taskDuration }
            .fold(Duration.ZERO, Duration::plus)

        println("+++ elapsedDuration: $elapsedDuration | pastTaskDuration: $pastTaskDuration")

        if (elapsedDuration >= pastTaskDuration) {
            // we either finished the session...
            if (currentTaskIndex == compiledSession.taskList.lastIndex) {
                resetTimerUseCase.execute()
                return UiState.Finished
            }

            // or have to start the next task
            currentTaskIndex++
        }

        return UiState.Running(
            sessionTitle = compiledSession.sessionTitle,
            currentTask = compiledSession.taskList[currentTaskIndex],
            timerMode = timerStatus.mode,
            elapsedDuration = elapsedDuration,
            totalDuration = compiledSession.totalDuration
        )
    }

    fun onStartSession() {
        doWhenSuccess {
            startTimerUseCase.execute(this.totalDuration)
        }
    }

    fun onPauseSession() {
        pauseTimerUseCase.execute()
    }

    fun onResetSession() {
        resetTimerUseCase.execute()
    }

    private fun doWhenSuccess(action: UiState.Running.() -> Unit) {
        val currentUiState = _uiState.value
        if (currentUiState is UiState.Running) {
            action(currentUiState)
        }
    }
}
