package com.example.trainingapp.data

import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.data.entities.Serie
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.data.entities.TrainingHistory
import kotlinx.coroutines.flow.Flow

interface TrainingsRepository {

    // Training related methods
    fun getAllTrainingsStream() : Flow<List<Training>>
    fun getTrainingStream(id: Int) : Flow<Training?>
    suspend fun insertTraining(training: Training): Long
    suspend fun deleteTraining(training: Training)
    suspend fun updateTraining(training: Training)

    // Exercise related methods
    fun getAllExercisesStream() : Flow<List<Exercise>>
    fun getExerciseStream(id: Int) : Flow<Exercise?>
    suspend fun insertExercise(exercise: Exercise)
    suspend fun updateExercise(exercise: Exercise)
    suspend fun deleteExercise(trainingId: Int, exerciseId: Int)
    suspend fun deleteAllExercises(trainingId: Int)

    // Serie related methods
    fun getAllSetsStream() : Flow<List<Serie>>
    fun getSetsForExerciseStream(exerciseId: Int) : Flow<List<Serie>>
    fun getSetStream(id: Int) : Flow<Serie?>
    suspend fun insertSet(serie: Serie)
    suspend fun updateSet(serie: Serie)
    suspend fun deleteSet(id: Int)
    suspend fun deleteSetsForExercise(exerciseId: Int)

    // TrainingHistory related methods
    fun getAllTrainingHistoriesStream() : Flow<List<TrainingHistory>>
    fun getTrainingHistoryStream(id: Int) : Flow<TrainingHistory?>
    suspend fun insertTrainingHistory(trainingHistory: TrainingHistory)
    suspend fun updateTrainingHistory(trainingHistory: TrainingHistory)
    suspend fun deleteTrainingHistory(trainingHistory: TrainingHistory)
}