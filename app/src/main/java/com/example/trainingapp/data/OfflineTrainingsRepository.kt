package com.example.trainingapp.data

import com.example.trainingapp.data.daos.ExerciseDao
import com.example.trainingapp.data.daos.SerieDao
import com.example.trainingapp.data.daos.TrainingDao
import com.example.trainingapp.data.daos.TrainingHistoryDao
import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.data.entities.Serie
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.data.entities.TrainingHistory
import kotlinx.coroutines.flow.Flow

class OfflineTrainingsRepository(
    private val trainingDao: TrainingDao,
    private val exerciseDao: ExerciseDao,
    private val serieDao: SerieDao,
    private val trainingHistoryDao: TrainingHistoryDao
) : TrainingsRepository {

    // Training methods
    override fun getAllTrainingsStream(): Flow<List<Training>> = trainingDao.getAllItems()
    override fun getTrainingStream(id: Int): Flow<Training?> = trainingDao.getItem(id)
    override suspend fun insertTraining(training: Training): Long = trainingDao.insert(training)
    override suspend fun deleteTraining(training: Training) = trainingDao.delete(training)
    override suspend fun updateTraining(training: Training) = trainingDao.update(training)

    // Exercise methods
    override fun getAllExercisesStream(): Flow<List<Exercise>> = exerciseDao.getAllExercises()
    override fun getExerciseStream(id: Int): Flow<Exercise?> = exerciseDao.getExercise(id)
    override suspend fun insertExercise(exercise: Exercise) = exerciseDao.insertExercise(exercise)
    override suspend fun updateExercise(exercise: Exercise) = exerciseDao.updateExercise(exercise)
    override suspend fun deleteExercise(trainingId: Int, exerciseId: Int) = exerciseDao.deleteExercise(trainingId, exerciseId)
    override suspend fun deleteAllExercises(trainingId: Int) = exerciseDao.deleteAllExercices(trainingId)

    // Set methods
    override fun getAllSetsStream(): Flow<List<Serie>> = serieDao.getAllSeries()
    override fun getSetsForExerciseStream(exerciseId: Int): Flow<List<Serie>> = serieDao.getSeriesForExercise(exerciseId)
    override fun getSetStream(id: Int): Flow<Serie?> = serieDao.getSerie(id)
    override suspend fun insertSet(serie: Serie) = serieDao.insertSerie(serie)
    override suspend fun updateSet(serie: Serie) = serieDao.updateSerie(serie)
    override suspend fun deleteSet(id: Int) = serieDao.deleteSet(id)
    override suspend fun deleteSetsForExercise(exerciseId: Int) = serieDao.deleteSetsForExercise(exerciseId)

    // TrainingHistory methods
    override fun getAllTrainingHistoriesStream(): Flow<List<TrainingHistory>> = trainingHistoryDao.getAllTrainingHistories()
    override fun getTrainingHistoryStream(id: Int): Flow<TrainingHistory?> = trainingHistoryDao.getTrainingHistory(id)
    override suspend fun insertTrainingHistory(trainingHistory: TrainingHistory) = trainingHistoryDao.insert(trainingHistory)
    override suspend fun updateTrainingHistory(trainingHistory: TrainingHistory) = trainingHistoryDao.update(trainingHistory)
    override suspend fun deleteTrainingHistory(trainingHistory: TrainingHistory) = trainingHistoryDao.delete(trainingHistory)
}