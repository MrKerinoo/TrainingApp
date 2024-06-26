package com.example.trainingapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.data.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * HomeViewModel is the ViewModel that provides data for the Home screen.
 * It uses TrainingsRepository to get the list of all trainings.
 */
class HomeViewModel(private val trainingsRepository: TrainingsRepository) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        trainingsRepository.getAllTrainingsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    fun deleteTraining(training: Training) {
        viewModelScope.launch {
            trainingsRepository.deleteTraining(training)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val trainingList: List<Training> = listOf())