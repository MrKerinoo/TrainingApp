package com.example.trainingapp.ui.training

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.TrainingsRepository
import com.example.trainingapp.data.entities.Exercise
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ExerciseEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val trainingsRepository: TrainingsRepository,
) : ViewModel()
{
    private val exerciseId: Int = checkNotNull(savedStateHandle[ExerciseEditDestination.exerciseIdArg])
    private val trainingId: Int = checkNotNull(savedStateHandle[ExerciseEditDestination.trainingIdArg])

    var exerciseUiState by mutableStateOf(ExerciseUiState())
        private set

    init {
        viewModelScope.launch {
            exerciseUiState = trainingsRepository.getExerciseStream(exerciseId)
                .filterNotNull()
                .first()
                .toExerciseUiState(true)
        }
    }

    fun updateUiState(exerciseDetails: ExerciseDetails) {
        exerciseUiState = ExerciseUiState(
            exerciseDetails = exerciseDetails,
            isEntryValid = validateInput(exerciseDetails))
    }



    suspend fun deleteExercise() {
        trainingsRepository.deleteExercise(exerciseUiState.exerciseDetails.toExercise(trainingId))
    }

    suspend fun updateExercise() {
        if (validateInput(exerciseUiState.exerciseDetails)) {
            trainingsRepository.updateExercise(exerciseUiState.exerciseDetails.toExercise(trainingId))
        }
    }

    private fun validateInput(uiState: ExerciseDetails = exerciseUiState.exerciseDetails): Boolean {
        val isValid = with(uiState) {
            name.isNotBlank() && sets.isNotBlank() && sets.toInt() > 0 && reps.isNotBlank() && reps.toInt() > 0
        }
        Log.d("ExerciseEditViewModel", "validateInput: isValid = $isValid")
        return isValid
    }
}
