package com.example.trainingapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trainingapp.data.entities.Serie
import kotlinx.coroutines.flow.Flow

@Dao
interface SerieDao {

    @Query("SELECT * FROM series")
    fun getAllSeries(): Flow<List<Serie>>

    @Query("SELECT * FROM series WHERE exerciseId = :exerciseId")
    fun getSeriesForExercise(exerciseId: Int): Flow<List<Serie>>

    @Query("SELECT * FROM series WHERE id = :id")
    fun getSerie(id: Int): Flow<Serie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerie(serie: Serie)

    @Update
    suspend fun updateSerie(serie: Serie)

    @Query("DELETE FROM series WHERE id = :id")
    suspend fun deleteSet(id: Int)

    @Query("DELETE FROM series WHERE exerciseId = :exerciseId")
    suspend fun deleteSetsForExercise(exerciseId: Int)
}