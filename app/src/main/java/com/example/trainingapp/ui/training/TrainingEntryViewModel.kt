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
import kotlinx.coroutines.launch

class TrainingEntryViewModel (
    savedStateHandle: SavedStateHandle,
    private val trainingsRepository: TrainingsRepository,
) : ViewModel()
{
    // Exercises
    private val exercises = mutableListOf<Exercise>()

    // Trainings
    var trainingUiState by mutableStateOf(TrainingUiState())
        private set

    fun validateInput(uiState: TrainingDetails  = trainingUiState.trainingDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    fun updateUiState(trainingDetails: TrainingDetails) {
        trainingUiState = TrainingUiState(
            trainingDetails = trainingDetails,
            isEntryValid = validateInput(trainingDetails))
    }

    suspend fun saveTraining() {
        if (validateInput()) {
            viewModelScope.launch {
                // Save the Training first to generate an ID
                val trainingId = trainingsRepository.insertTraining(trainingUiState.trainingDetails.toTraining())

                // Then save each Exercise, linking it to the Training using its ID
                exercises.forEach { exercise ->
                    val exerciseWithTrainingId = exercise.copy(trainingId = trainingId.toInt())
                    trainingsRepository.insertExercise(exerciseWithTrainingId)
                }
            }
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

fun Training.toTrainingUiState(isEntryValid: Boolean = false): TrainingUiState = TrainingUiState(
    trainingDetails = this.toTrainingDetails(),
    isEntryValid = isEntryValid
)

fun Training.toTrainingDetails(): TrainingDetails = TrainingDetails(
    id = id,
    name = name
)

