package com.example.trainingapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.data.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel(private val trainingsRepository: TrainingsRepository) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        trainingsRepository.getAllTrainingsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun deleteTraining(training: Training) {
        viewModelScope.launch {
            trainingsRepository.deleteTraining(training)
        }
    }
}

data class HomeUiState(val trainingList: List<Training> = listOf())