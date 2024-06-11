package com.example.trainingapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.trainingapp.TrainingApplication
import com.example.trainingapp.ui.history.HistoryViewModel
import com.example.trainingapp.ui.home.HomeViewModel
import com.example.trainingapp.ui.exercise.ExerciseEditViewModel
import com.example.trainingapp.ui.exercise.ExerciseEntryViewModel
import com.example.trainingapp.ui.profile.UserViewModel
import com.example.trainingapp.ui.training.TrainingEditViewModel
import com.example.trainingapp.ui.training.TrainingEntryViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(trainingApplication().container.trainingsRepository)
        }

        initializer {
            HistoryViewModel(
                trainingApplication().container.trainingsRepository)
        }

        initializer {
            UserViewModel(
                trainingApplication().container.trainingsRepository)
        }

        initializer {
            TrainingEntryViewModel(
                trainingsRepository = trainingApplication().container.trainingsRepository)
        }

        initializer {
            ExerciseEntryViewModel(
                trainingsRepository = trainingApplication().container.trainingsRepository)
        }

        initializer {
            TrainingEditViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                trainingsRepository = trainingApplication().container.trainingsRepository)
        }

        initializer {
            ExerciseEditViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                trainingsRepository = trainingApplication().container.trainingsRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [TrainingApplication].
 */
fun CreationExtras.trainingApplication(): TrainingApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TrainingApplication)
