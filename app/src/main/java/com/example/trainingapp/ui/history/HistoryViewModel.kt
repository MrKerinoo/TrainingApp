package com.example.trainingapp.ui.history

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.Training
import com.example.trainingapp.data.TrainingsRepository
import com.example.trainingapp.ui.home.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class HistoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val trainingsRepository: TrainingsRepository,
    ) : ViewModel() {

    val historyUiState: StateFlow<HistoryUiState> =
        trainingsRepository.getAllTrainingsStream().map { HistoryUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HistoryUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


data class HistoryUiState(
    val trainingList: List<Training> = listOf()
)