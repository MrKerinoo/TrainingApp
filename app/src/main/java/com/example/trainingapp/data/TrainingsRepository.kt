package com.example.trainingapp.data

import kotlinx.coroutines.flow.Flow

interface TrainingsRepository {
    /**
     * Get all trainings
     */
    fun getAllTrainingsStream() : Flow<List<Training>>

    /**
     * Get training by id
     */
    fun getTrainingStream(id: Int) : Flow<Training?>

    /**
     * Insert training in the data source
     */

    suspend fun insertTraining(training: Training)

    /**
     * Delete training from the data source
     */
    suspend fun deleteTraining(training: Training)

    /**
     * Update training in the data source
     */
    suspend fun updateTraining(training: Training)
}