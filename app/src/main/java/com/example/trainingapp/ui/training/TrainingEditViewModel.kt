package com.example.trainingapp.ui.training

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.TrainingsRepository
import com.example.trainingapp.data.entities.Exercise
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrainingEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val trainingsRepository: TrainingsRepository
) : ViewModel()
{
    private val trainingId: Int = checkNotNull(savedStateHandle[TrainingEditDestination.trainingIdArg])

    val exercisesUiState: StateFlow<ExercisesUiState> =
        trainingsRepository.getExercisesForTrainingStream(trainingId).map {ExercisesUiState(it)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ExercisesUiState()
            )


    var trainigUiState by mutableStateOf(TrainingUiState())
        private set

    init {
        viewModelScope.launch {
            trainigUiState = trainingsRepository.getTrainingStream(trainingId)
                .filterNotNull()
                .first()
                .toTrainingUiState(true)
        }
    }

    suspend fun deleteTraining()
    {
        trainingsRepository.deleteTraining(trainigUiState.trainingDetails.toTraining())
    }

    suspend fun updateTraining() {
        if (validateInput(trainigUiState.trainingDetails)) {
            trainingsRepository.updateTraining(trainigUiState.trainingDetails.toTraining())
        }
    }

    fun updateUiState(trainingDetails: TrainingDetails) {
        trainigUiState = TrainingUiState(
            trainingDetails = trainingDetails,
            isEntryValid = validateInput(trainingDetails))
    }

    private fun validateInput(uiState: TrainingDetails = trainigUiState.trainingDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }
}

data class ExercisesUiState(
    val exerciseList: List<Exercise> = listOf()
)