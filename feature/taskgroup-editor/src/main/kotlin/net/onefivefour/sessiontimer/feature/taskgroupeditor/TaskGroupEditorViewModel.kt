package net.onefivefour.sessiontimer.feature.taskgroupeditor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.onefivefour.sessiontimer.core.common.domain.model.TaskGroup
import net.onefivefour.sessiontimer.core.usecases.GetTaskGroupUseCase
import javax.inject.Inject

@HiltViewModel
internal class TaskGroupEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTaskGroupUseCase: GetTaskGroupUseCase
) : ViewModel() {

    private val taskGroupId = checkNotNull(savedStateHandle.get<Long>("taskGroupId"))

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getTaskGroupUseCase.execute(taskGroupId).collectLatest { taskGroup ->
                _uiState.update {
                    UiState.Success(taskGroup)
                }
            }
        }
    }

}

internal sealed interface UiState {
    data object Initial : UiState
    data class Success(val taskGroup: TaskGroup) : UiState
}