package com.example.trainingapp.data

import kotlinx.coroutines.flow.Flow

class OfflineTrainingsRepository(private val trainingDao: TrainingDao) : TrainingsRepository {
    override fun getAllTrainingsStream(): Flow<List<Training>> = trainingDao.getAllItems()

    override fun getTrainingStream(id: Int): Flow<Training?> = trainingDao.getItem(id)

    override suspend fun insertTraining(training: Training) = trainingDao.insert(training)

    override suspend fun deleteTraining(training: Training) = trainingDao.delete(training)

    override suspend fun updateTraining(training: Training) = trainingDao.update(training)
}