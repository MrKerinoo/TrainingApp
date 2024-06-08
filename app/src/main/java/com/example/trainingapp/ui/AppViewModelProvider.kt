/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.example.trainingapp.ui.training.ExerciseEntryViewModel
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
                this.createSavedStateHandle(),
                trainingApplication().container.trainingsRepository)
        }

        initializer {
            TrainingEntryViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                trainingsRepository = trainingApplication().container.trainingsRepository)
        }

        initializer {
            ExerciseEntryViewModel(
                savedStateHandle = this.createSavedStateHandle()
            )
        }

    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [TrainingApplication].
 */
fun CreationExtras.trainingApplication(): TrainingApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TrainingApplication)
