package com.example.trainingapp.ui.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.trainingapp.data.TrainingsRepository
import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.data.entities.ExerciseHistory

class ExerciseEntryViewModel (
    savedStateHandle: SavedStateHandle,
    private val trainingsRepository: TrainingsRepository,
) : ViewModel()
{
    var exerciseUiState by mutableStateOf(ExerciseUiState())
        private set


    fun updateUiState(exerciseDetails: ExerciseDetails) {
        exerciseUiState = ExerciseUiState(
            exerciseDetails = exerciseDetails,
            isEntryValid = validateInput(exerciseDetails))
    }

    suspend fun saveExercise(trainingId: Int) {
        if (validateInput()) {
            trainingsRepository.insertExercise(exerciseUiState.exerciseDetails.toExercise(trainingId))
        }
    }

    private fun validateInput(uiState: ExerciseDetails = exerciseUiState.exerciseDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && sets.isNotBlank() && sets.toInt() > 0 && reps.isNotBlank() && reps.toInt() > 0
        }
    }
}

data class ExerciseUiState(
    val exerciseDetails: ExerciseDetails = ExerciseDetails(),
    val isEntryValid: Boolean = false
)

data class ExerciseDetails(
    val id: Int = 0,
    val name: String = "",
    val sets: String = "",
    val reps: String = "",
    val weight: String = "",
)

fun ExerciseDetails.toExercise(trainingId: Int): Exercise {
    if (weight.isBlank()) {
        return Exercise(
            id = id,
            name = name,
            sets = sets.toInt(),
            reps = reps.toInt(),
            weight = 0f,
            trainingId = trainingId
        )
    } else
    {
        return Exercise(
            id = id,
            name = name,
            sets = sets.toInt(),
            reps = reps.toInt(),
            weight = weight.toFloat(),
            trainingId = trainingId
        )
    }
}

fun ExerciseDetails.toExerciseHistory(trainingHistoryId: Int): ExerciseHistory {
    if (weight.isBlank()) {
        return ExerciseHistory(
            name = name,
            sets = sets.toInt(),
            reps = reps.toInt(),
            weight = 0f,
            trainingId = trainingHistoryId
        )
    } else {
        return ExerciseHistory(
            name = name,
            sets = sets.toInt(),
            reps = reps.toInt(),
            weight = weight.toFloat(),
            trainingId = trainingHistoryId
        )
    }
}

fun Exercise.toExerciseUiState(isEntryValid: Boolean = false): ExerciseUiState = ExerciseUiState(
    exerciseDetails = this.toExerciseDetails(),
    isEntryValid = isEntryValid
)

fun Exercise.toExerciseDetails(): ExerciseDetails = ExerciseDetails(
    id = id,
    name = name,
    sets = sets.toString(),
    reps = reps.toString(),
    weight = weight.toString(),
)

