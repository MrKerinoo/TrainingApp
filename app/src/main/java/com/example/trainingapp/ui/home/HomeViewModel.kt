package com.example.trainingapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.Training
import com.example.trainingapp.data.TrainingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
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

    fun addTraining() {
        viewModelScope.launch {
            val allTrainings = trainingsRepository.getAllTrainingsStream().first()

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val date1 = dateFormat.parse("25.04.2024")
            val training1 = Training(
                name = "FullBody workout",
                date = date1 ?: Date()
            )
            trainingsRepository.insertTraining(training1)

            val date2 = dateFormat.parse("15.05.2024")
            val training2 = Training(
                name = "Upper workout",
                date = date2 ?: Date()
            )
            trainingsRepository.insertTraining(training2)

            val date3 = dateFormat.parse("5.03.2024")
            val training3 = Training(
                name = "Lower workout",
                date = date3 ?: Date()
            )
            trainingsRepository.insertTraining(training3)
        }
    }

    fun deleteAllTrainings() {
        viewModelScope.launch {
            val allTrainings = trainingsRepository.getAllTrainingsStream().first()

            allTrainings.forEach { training ->
                trainingsRepository.deleteTraining(training)
            }
        }
    }

    /*
    init {
        addTraining()
    }
    */
}



data class HomeUiState(val trainingList: List<Training> = listOf())