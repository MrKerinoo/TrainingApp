package com.example.trainingapp.ui.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.TrainingsRepository
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
                .toExerciseUiState(true, true ,true, true)
        }
    }

    fun updateUiState(exerciseDetails: ExerciseDetails) {
        exerciseUiState = ExerciseUiState(
            exerciseDetails = exerciseDetails,
            isNameValid = validateName(exerciseDetails),
            isSetsValid = validateSets(exerciseDetails),
            isRepsValid = validateReps(exerciseDetails),
            isWeightValid = validateWeight(exerciseDetails)
        )
    }

    suspend fun deleteExercise() {
        trainingsRepository.deleteExercise(exerciseUiState.exerciseDetails.id)
    }

    suspend fun updateExercise() {
        if (validateName(exerciseUiState.exerciseDetails) &&
            validateSets(exerciseUiState.exerciseDetails) &&
            validateReps(exerciseUiState.exerciseDetails) &&
            validateWeight(exerciseUiState.exerciseDetails)
            )
        {
            trainingsRepository.updateExercise(exerciseUiState.exerciseDetails.toExercise(trainingId))
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
