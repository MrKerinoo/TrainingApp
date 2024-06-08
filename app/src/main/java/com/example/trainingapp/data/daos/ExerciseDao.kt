package com.example.trainingapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trainingapp.data.entities.Exercise
import kotlinx.coroutines.flow.Flow
@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * from exercises WHERE id = :id")
    fun getExercise(id: Int): Flow<Exercise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Query("DELETE FROM exercises WHERE trainingId = :trainingId AND id = :exerciseId")
    suspend fun deleteExercise(trainingId: Int, exerciseId: Int)

    @Query("DELETE FROM exercises WHERE trainingId = :trainingId")
    suspend fun deleteAllExercices(trainingId: Int)
}