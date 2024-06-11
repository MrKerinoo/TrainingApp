package com.example.trainingapp.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.TrainingsRepository
import com.example.trainingapp.data.entities.ExerciseHistory
import com.example.trainingapp.data.entities.TrainingHistory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * HistoryViewModel is the ViewModel that provides data for the History screen.
 * It uses TrainingsRepository to get the list of all training histories.
 */
class HistoryViewModel(
    private val trainingsRepository: TrainingsRepository,
    ) : ViewModel() {

    val historyUiState: StateFlow<HistoryUiState> =
        trainingsRepository.getAllTrainingHistoriesStream().map { HistoryUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HistoryUiState()
            )

    val exerciseHistoryUiState: StateFlow<ExerciseHistoryUiState> =
        trainingsRepository.getAllExerciseHistoriesStream().map { ExerciseHistoryUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ExerciseHistoryUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


data class HistoryUiState(
    val trainingList: List<TrainingHistory> = listOf()
)

data class ExerciseHistoryUiState(
    val exerciseHistoryList: List<ExerciseHistory> = listOf()
)