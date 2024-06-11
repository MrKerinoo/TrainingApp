package com.example.trainingapp.ui.training

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.TypeConverters
import com.example.trainingapp.data.Converters
import com.example.trainingapp.data.TrainingsRepository
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.data.entities.TrainingHistory
import kotlinx.coroutines.launch
import java.util.Date

/**
 * TrainingEntryViewModel is the ViewModel that provides data for the TrainingEntry screen.
 * It uses TrainingsRepository to save the training entry.
 * Validates the input data before saving the training entry.
 * Updates UiState and saves the training entry.
 * Transforms TrainingDetails to Training and TrainingHistory.
 */
class TrainingEntryViewModel (
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
}

data class TrainingUiState(
    val trainingDetails: TrainingDetails = TrainingDetails(),
    val isEntryValid: Boolean = false
)

data class TrainingDetails(
    val id: Int = 0,
    val name: String = "",
    @TypeConverters(Converters::class)
    var date: Date? = null
)

fun TrainingDetails.toTraining(): Training = Training(
    id = id,
    name = name,
    date = date
)

fun TrainingDetails.toTrainingHistory(timer: Int): TrainingHistory = TrainingHistory(
    name = name,
    time = timer
)

fun Training.toTrainingUiState(isEntryValid: Boolean = false): TrainingUiState = TrainingUiState(
    trainingDetails = this.toTrainingDetails(),
    isEntryValid = isEntryValid
)

fun Training.toTrainingDetails(): TrainingDetails = TrainingDetails(
    id = id,
    name = name,
    date = date
)

