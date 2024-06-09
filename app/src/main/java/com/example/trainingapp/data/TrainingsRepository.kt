package com.example.trainingapp.data

import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.data.entities.Training
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
    fun getExercisesForTrainingStream(trainingId: Int) : Flow<List<Exercise>>
    suspend fun insertExercise(exercise: Exercise)
    suspend fun updateExercise(exercise: Exercise)
    suspend fun deleteExercise(exercise: Exercise)

}