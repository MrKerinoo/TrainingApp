package com.example.trainingapp.data

import com.example.trainingapp.data.daos.ExerciseDao
import com.example.trainingapp.data.daos.ExerciseHistoryDao
import com.example.trainingapp.data.daos.TrainingDao
import com.example.trainingapp.data.daos.TrainingHistoryDao
import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.data.entities.ExerciseHistory
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.data.entities.TrainingHistory
import kotlinx.coroutines.flow.Flow

class OfflineTrainingsRepository(
    private val trainingDao: TrainingDao,
    private val exerciseDao: ExerciseDao,
    private val trainingHistoryDao: TrainingHistoryDao,
    private val exerciseHistoryDao: ExerciseHistoryDao

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
    override fun getExercisesForTrainingStream(trainingId: Int): Flow<List<Exercise>> = exerciseDao.getExercisesByTrainingId(trainingId)
    override suspend fun insertExercise(exercise: Exercise) = exerciseDao.insertExercise(exercise)
    override suspend fun updateExercise(exercise: Exercise) = exerciseDao.updateExercise(exercise)
    override suspend fun deleteExercise(exercise: Exercise) = exerciseDao.deleteExercise(exercise)

    // TrainingHistory methods
    override fun getAllTrainingHistoriesStream(): Flow<List<TrainingHistory>> = trainingHistoryDao.getAllTrainingHistories()
    override fun getTrainingHistoryStream(id: Int): Flow<TrainingHistory?> = trainingHistoryDao.getTrainingHistory(id)
    override suspend fun insertTrainingHistory(trainingHistory: TrainingHistory): Long = trainingHistoryDao.insert(trainingHistory)
    override suspend fun updateTrainingHistory(trainingHistory: TrainingHistory) = trainingHistoryDao.update(trainingHistory)
    override suspend fun deleteTrainingHistory(trainingHistory: TrainingHistory) = trainingHistoryDao.delete(trainingHistory)

    override fun getAllExerciseHistoriesStream(): Flow<List<ExerciseHistory>> = exerciseHistoryDao.getAllExercisesHistory()
    override fun getExerciseHistoryStream(id: Int): Flow<ExerciseHistory?> = exerciseHistoryDao.getExercisegetAllExercisesHistory(id)
    override fun getExerciseHistoriesForTrainingStream(trainingId: Int): Flow<List<ExerciseHistory>> = exerciseHistoryDao.getExercisesByTrainingIdHistory(trainingId)
    override suspend fun insertExerciseHistory(exerciseHistory: ExerciseHistory) = exerciseHistoryDao.insertExerciseHistory(exerciseHistory)
    override suspend fun deleteExerciseHistory(exerciseHistory: ExerciseHistory) = exerciseHistoryDao.deleteExerciseHistory(exerciseHistory)
}