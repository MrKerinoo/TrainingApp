package com.example.trainingapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trainingapp.data.entities.Set
import kotlinx.coroutines.flow.Flow

@Dao
interface SetDao {

    @Query("SELECT * FROM sets")
    fun getAllSets(): Flow<List<Set>>

    @Query("SELECT * FROM sets WHERE exerciseId = :exerciseId")
    fun getSetsForExercise(exerciseId: Int): Flow<List<Set>>

    @Query("SELECT * FROM sets WHERE id = :id")
    fun getSet(id: Int): Flow<Set>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: Set)

    @Update
    suspend fun updateSet(set: Set)

    @Query("DELETE FROM sets WHERE id = :id")
    suspend fun deleteSet(id: Int)

    @Query("DELETE FROM sets WHERE exerciseId = :exerciseId")
    suspend fun deleteSetsForExercise(exerciseId: Int)
}