package com.example.trainingapp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trainingapp.data.entities.Training
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {

    @Query("SELECT * FROM trainings ORDER BY name ASC")
    fun getAllItems(): Flow<List<Training>>

    @Query("SELECT * from trainings WHERE id = :id")
    fun getItem(id: Int): Flow<Training>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(training: Training): Long

    @Update
    suspend fun update(training: Training)

    @Delete
    suspend fun delete(training: Training)
}