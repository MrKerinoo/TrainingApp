package com.example.trainingapp.data

import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.data.entities.ExerciseHistory
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.data.entities.TrainingHistory
import com.example.trainingapp.data.entities.User
import kotlinx.coroutines.flow.Flow

/**
 * TrainingsRepository is the interface that defines the methods to interact with the database.
 */
interface TrainingsRepository {

    //
    suspend fun deleteDatabase()
    suspend fun deleteHistoryDatabase()

    // Training related methods
    fun getAllTrainingsStream() : Flow<List<Training>>
    fun getTrainingStream(id: Int) : Flow<Training?>
    suspend fun insertTraining(training: Training): Long
    suspend fun deleteTraining(training: Training)
    suspend fun updateTraining(training: Training)

    // Exercise related methods
    fun getAllExercisesStream() : Flow<List<Exercise>>
    fun getExerciseStream(id: Int) : Flow<Exercise?>
    fun getExercisesForTrainingStream(trainingId: Int) : Flow<List<Exercise>>
    suspend fun insertExercise(exercise: Exercise)
    suspend fun updateExercise(exercise: Exercise)
    suspend fun deleteExercise(id: Int)

    // TrainingHistory related methods
    fun getAllTrainingHistoriesStream() : Flow<List<TrainingHistory>>
    fun getTrainingHistoryStream(id: Int) : Flow<TrainingHistory?>
    suspend fun insertTrainingHistory(trainingHistory: TrainingHistory): Long
    suspend fun updateTrainingHistory(trainingHistory: TrainingHistory)
    suspend fun deleteTrainingHistory(trainingHistory: TrainingHistory)

    // ExerciseHistory related methods
    fun getAllExerciseHistoriesStream() : Flow<List<ExerciseHistory>>
    fun getExerciseHistoryStream(id: Int) : Flow<ExerciseHistory?>
    fun getExerciseHistoriesForTrainingStream(trainingId: Int) : Flow<List<ExerciseHistory>>
    suspend fun insertExerciseHistory(exerciseHistory: ExerciseHistory)
    suspend fun deleteExerciseHistory(exerciseHistory: ExerciseHistory)

    // Profile related methods
    fun getUserStream() : Flow<User?>
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
}