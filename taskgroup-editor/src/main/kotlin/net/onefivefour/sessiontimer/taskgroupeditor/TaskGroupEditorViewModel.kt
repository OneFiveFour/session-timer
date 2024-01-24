package net.onefivefour.sessiontimer.taskgroupeditor

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.onefivefour.sessiontimer.database.domain.model.TaskGroup
import javax.inject.Inject

@HiltViewModel
internal class TaskGroupEditorViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

}

internal sealed interface UiState {
    data object Initial : UiState
    data class Success(val taskGroup: TaskGroup) : UiState
}