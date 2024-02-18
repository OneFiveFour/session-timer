package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.usecases.taskgroup.GetTaskGroupUseCase
import net.onefivefour.sessiontimer.core.usecases.taskgroup.UpdateTaskGroupUseCase
import javax.inject.Inject

@HiltViewModel
internal class TaskGroupEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTaskGroupUseCase: GetTaskGroupUseCase,
    private val updateTaskGroupUseCase: UpdateTaskGroupUseCase
) : ViewModel() {

    private val taskGroupId = checkNotNull(savedStateHandle.get<Long>(NAV_ARG_TASK_GROUP_ID))

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getTaskGroupUseCase.execute(taskGroupId).collectLatest { taskGroup ->
                _uiState.update {
                    UiState.Success(taskGroup.toUiTaskGroup())
                }
            }
        }
    }

    fun updateTaskGroup(taskGroup: UiTaskGroup) {
        viewModelScope.launch {
            updateTaskGroupUseCase.execute(
                taskGroup.id,
                taskGroup.title,
                taskGroup.color.toArgb(),
                taskGroup.playMode,
                taskGroup.numberOfRandomTasks
            )
        }
    }
}

