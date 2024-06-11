package com.example.trainingapp.ui.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.example.trainingapp.data.TrainingsRepository
import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.data.entities.ExerciseHistory

/**
 * ExerciseEntryViewModel is the ViewModel that provides data for the ExerciseEntry screen.
 * It uses TrainingsRepository to save the exercise entry.
 * Validates the input data before saving the exercise entry.
 * Updates UiState and saves the exercise entry.
 * Transforms ExerciseDetails to Exercise and ExerciseHistory.
 */
class ExerciseEntryViewModel (
    private val trainingsRepository: TrainingsRepository,
) : ViewModel()
{
    var exerciseUiState by mutableStateOf(ExerciseUiState())
        private set


    fun updateUiState(exerciseDetails: ExerciseDetails) {
        exerciseUiState = ExerciseUiState(
            exerciseDetails = exerciseDetails,
            isNameValid = validateName(exerciseDetails),
            isSetsValid = validateSets(exerciseDetails),
            isRepsValid = validateReps(exerciseDetails),
            isWeightValid = validateWeight(exerciseDetails)
        )
    }

    suspend fun saveExercise(trainingId: Int) {
        if (validateName(exerciseUiState.exerciseDetails) &&
            validateSets(exerciseUiState.exerciseDetails) &&
            validateReps(exerciseUiState.exerciseDetails))
        {
            trainingsRepository.insertExercise(exerciseUiState.exerciseDetails.toExercise(trainingId))
        }
    }

    private fun validateName(uiState: ExerciseDetails = exerciseUiState.exerciseDetails): Boolean {
        val isValid = with(uiState) {
            name.isNotBlank()
        }
        return isValid
    }

    private fun validateSets(uiState: ExerciseDetails = exerciseUiState.exerciseDetails): Boolean {
        val isValid = with(uiState) {
            sets.isNotBlank() && sets.isDigitsOnly()
        }
        return isValid
    }

    private fun validateReps(uiState: ExerciseDetails = exerciseUiState.exerciseDetails): Boolean {
        val isValid = with(uiState) {
            reps.isNotBlank() && reps.isDigitsOnly()
        }
        return isValid
    }

    private fun validateWeight(uiState: ExerciseDetails = exerciseUiState.exerciseDetails): Boolean {
        val isValid = with(uiState) {
            weight.isDigitsOnly()
        }
        return isValid
    }
}

data class ExerciseUiState(
    val exerciseDetails: ExerciseDetails = ExerciseDetails(),
    val isNameValid: Boolean = false,
    val isSetsValid: Boolean = false,
    val isRepsValid: Boolean = false,
    val isWeightValid: Boolean = true
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

fun Exercise.toExerciseUiState(
    isNameValid: Boolean = false,
    isSetsValid: Boolean = false,
    isRepsValid: Boolean = false,
    isWeightValid: Boolean = false): ExerciseUiState = ExerciseUiState(
    exerciseDetails = this.toExerciseDetails(),
    isNameValid = isNameValid,
    isSetsValid = isSetsValid,
    isRepsValid = isRepsValid,
    isWeightValid = isWeightValid
)

fun Exercise.toExerciseDetails(): ExerciseDetails = ExerciseDetails(
    id = id,
    name = name,
    sets = sets.toString(),
    reps = reps.toString(),
    weight = weight.toString(),
)

