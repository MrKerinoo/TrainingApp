package com.example.trainingapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trainingapp.data.entities.Exercise
import kotlinx.coroutines.flow.Flow

/**
 * ExerciseDao is a Data Access Object interface that contains methods to interact with the exercises table in the database.
 */
@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * from exercises WHERE id = :id")
    fun getExercise(id: Int): Flow<Exercise>

    @Query("SELECT * FROM exercises WHERE trainingId = :trainingId ORDER BY dateAdded ASC")
    fun getExercisesByTrainingId(trainingId: Int): Flow<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Query("DELETE FROM exercises WHERE id = :id")
    suspend fun deleteExercise(id: Int)
}