package com.example.trainingapp.ui.training

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.TrainingsRepository
import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.data.entities.TrainingHistory
import kotlinx.coroutines.launch

class TrainingEntryViewModel (
    savedStateHandle: SavedStateHandle,
    private val trainingsRepository: TrainingsRepository
) : ViewModel()
{
    // Trainings
    var trainingUiState by mutableStateOf(TrainingUiState())
        private set

    fun updateUiState(trainingDetails: TrainingDetails) {
        trainingUiState = TrainingUiState(
            trainingDetails = trainingDetails,
            isEntryValid = validateInput(trainingDetails))
    }

    suspend fun saveTraining() {
        if (validateInput()) {
            viewModelScope.launch {
                trainingsRepository.insertTraining(trainingUiState.trainingDetails.toTraining())
            }
        }
    }

    fun validateInput(uiState: TrainingDetails  = trainingUiState.trainingDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TrainingUiState(
    val trainingDetails: TrainingDetails = TrainingDetails(),
    val isEntryValid: Boolean = false
)

data class TrainingDetails(
    val id: Int = 0,
    val name: String = "",
)

fun TrainingDetails.toTraining(): Training = Training(
    id = id,
    name = name
)

fun TrainingDetails.toTrainingHistory(): TrainingHistory = TrainingHistory(
    name = name
)

fun Training.toTrainingUiState(isEntryValid: Boolean = false): TrainingUiState = TrainingUiState(
    trainingDetails = this.toTrainingDetails(),
    isEntryValid = isEntryValid
)

fun Training.toTrainingDetails(): TrainingDetails = TrainingDetails(
    id = id,
    name = name
)

