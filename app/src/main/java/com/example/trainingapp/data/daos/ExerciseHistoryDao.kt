package com.example.trainingapp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trainingapp.data.entities.ExerciseHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseHistoryDao {
    @Query("SELECT * FROM exercises_history ORDER BY name ASC")
    fun getAllExercisesHistory(): Flow<List<ExerciseHistory>>

    @Query("SELECT * from exercises_history WHERE id = :id")
    fun getExercisegetAllExercisesHistory(id: Int): Flow<ExerciseHistory>

    @Query("SELECT * FROM exercises_history WHERE trainingId = :trainingId ORDER BY dateAdded ASC")
    fun getExercisesByTrainingIdHistory(trainingId: Int): Flow<List<ExerciseHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseHistory(exerciseHistory: ExerciseHistory)

    @Delete
    suspend fun deleteExerciseHistory(exerciseHistory: ExerciseHistory)
}