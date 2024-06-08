package com.example.trainingapp.ui.training

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.trainingapp.data.entities.Exercise

class ExerciseEntryViewModel (
    savedStateHandle: SavedStateHandle,
) : ViewModel()
{
    var exerciseUiState by mutableStateOf(ExerciseUiState())
        private set

    fun updateUiState(exerciseDetails: ExerciseDetails) {
        exerciseUiState = ExerciseUiState(
            exerciseDetails = exerciseDetails,
            isEntryValid = validateInput(exerciseDetails))
    }

    suspend fun saveExercise() {
        if (validateInput()) {
        }
    }

    private fun validateInput(uiState: ExerciseDetails = exerciseUiState.exerciseDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && sets > 0 && reps > 0
        }
    }
}

data class ExerciseUiState(
    val exerciseDetails: ExerciseDetails = ExerciseDetails(),
    val isEntryValid: Boolean = false
)

data class ExerciseDetails(
    val name: String = "",
    val sets: Int = 0,
    val reps: Int = 0,
    val weight: Float = 0f
)

