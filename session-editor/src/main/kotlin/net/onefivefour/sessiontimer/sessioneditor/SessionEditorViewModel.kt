package net.onefivefour.sessiontimer.sessioneditor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.database.domain.model.Session
import javax.inject.Inject

@HiltViewModel
class SessionEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFullSessionUseCase: GetFullSessionUseCase,
    private val newTaskGroupUseCase: NewTaskGroupUseCase,
    private val newTaskUseCase: NewTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteTaskGroupUseCase: DeleteTaskGroupUseCase
) : ViewModel() {

    private val sessionId = checkNotNull(savedStateHandle.get<Long>("sessionId"))

    private var _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getFullSessionUseCase.execute(sessionId).collectLatest { fullSession ->
                _uiState.update {
                    it.copy(session = fullSession)
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
}

data class UiState(
    val session: Session? = null
)