package com.example.trainingapp.data

import android.content.Context

/**
 * AppContainer is an interface that defines the dependencies that the app uses.
 */
interface AppContainer{
    val trainingsRepository: TrainingsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val trainingsRepository: TrainingsRepository by lazy {
        OfflineTrainingsRepository(
            trainingDao = TrainingDatabase.getDatabase(context).trainingDao(),
            trainingHistoryDao = TrainingDatabase.getDatabase(context).historyDao(),
            exerciseDao = TrainingDatabase.getDatabase(context).exerciseDao(),
            exerciseHistoryDao = TrainingDatabase.getDatabase(context).exerciseHistoryDao(),
            profileDao = TrainingDatabase.getDatabase(context).profileDao()
        )
    }
}