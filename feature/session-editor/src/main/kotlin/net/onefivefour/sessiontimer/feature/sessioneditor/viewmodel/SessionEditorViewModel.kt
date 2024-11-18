package net.onefivefour.sessiontimer.feature.sessioneditor.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.usecases.session.GetFullSessionUseCase
import net.onefivefour.sessiontimer.core.usecases.task.DeleteTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.task.NewTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.task.UpdateTaskUseCase
import net.onefivefour.sessiontimer.core.usecases.taskgroup.DeleteTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.taskgroup.NewTaskGroupUseCase
import net.onefivefour.sessiontimer.feature.sessioneditor.api.SessionEditor
import net.onefivefour.sessiontimer.feature.sessioneditor.model.UiTask

@HiltViewModel
internal class SessionEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFullSessionUseCase: GetFullSessionUseCase,
    private val newTaskGroupUseCase: NewTaskGroupUseCase,
    private val newTaskUseCase: NewTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteTaskGroupUseCase: DeleteTaskGroupUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {

    private val sessionId = savedStateHandle.toRoute<SessionEditor>().sessionId

    private var _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getFullSessionUseCase.execute(sessionId).collectLatest { fullSession ->
                _uiState.update {
                    when (fullSession) {
                        null -> UiState.Error("Could not find a session with id $sessionId")
                        else -> UiState.Success(session = fullSession)
                    }
                }
            }
        }
    }

    fun newTaskGroup() {
        viewModelScope.launch {
            newTaskGroupUseCase.execute(sessionId)
        }
    }

    fun deleteTaskGroup(taskGroupId: Long) {
        viewModelScope.launch {
            deleteTaskGroupUseCase.execute(taskGroupId)
        }
    }

    fun newTask(taskGroupId: Long) {
        viewModelScope.launch {
            newTaskUseCase.execute(taskGroupId)
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            deleteTaskUseCase.execute(taskId)
        }
    }

    fun updateTask(task: UiTask) {
        viewModelScope.launch {
            updateTaskUseCase.execute(
                task.id,
                task.title,
                task.duration
            )
        }
    }
}
