package com.example.trainingapp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trainingapp.data.entities.TrainingHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingHistory: TrainingHistory)

    @Update
    suspend fun update(trainingHistory: TrainingHistory)

    @Delete
    suspend fun delete(trainingHistory: TrainingHistory)

    @Query("SELECT * FROM history")
    fun getAllTrainingHistories(): Flow<List<TrainingHistory>>

    @Query("SELECT * FROM history WHERE id = :id")
    fun getTrainingHistory(id: Int): Flow<TrainingHistory>
}