package com.example.trainingapp.data

import com.example.trainingapp.data.daos.ExerciseDao
import com.example.trainingapp.data.daos.TrainingDao
import com.example.trainingapp.data.daos.TrainingHistoryDao
import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.data.entities.TrainingHistory
import kotlinx.coroutines.flow.Flow

class OfflineTrainingsRepository(
    private val trainingDao: TrainingDao,
    private val exerciseDao: ExerciseDao,
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
    override fun getExercisesForTrainingStream(trainingId: Int): Flow<List<Exercise>> = exerciseDao.getExercisesByTrainingId(trainingId)
    override suspend fun insertExercise(exercise: Exercise) = exerciseDao.insertExercise(exercise)
    override suspend fun updateExercise(exercise: Exercise) = exerciseDao.updateExercise(exercise)
    override suspend fun deleteExercise(exercise: Exercise) = exerciseDao.deleteExercise(exercise)

}