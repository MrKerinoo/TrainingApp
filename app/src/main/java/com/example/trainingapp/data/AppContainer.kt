package com.example.trainingapp.data

import android.content.Context

interface AppContainer{
    val trainingsRepository: TrainingsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val trainingsRepository: TrainingsRepository by lazy {
        OfflineTrainingsRepository(
            trainingDao = TrainingDatabase.getDatabase(context).trainingDao(),
            trainingHistoryDao = TrainingDatabase.getDatabase(context).historyDao(),
            exerciseDao = TrainingDatabase.getDatabase(context).exerciseDao(),
        )
    }
}