package com.example.trainingapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trainingapp.data.daos.ExerciseDao
import com.example.trainingapp.data.daos.SerieDao
import com.example.trainingapp.data.daos.TrainingDao
import com.example.trainingapp.data.daos.TrainingHistoryDao
import com.example.trainingapp.data.entities.Exercise
import com.example.trainingapp.data.entities.Serie
import com.example.trainingapp.data.entities.Training
import com.example.trainingapp.data.entities.TrainingHistory

@Database(entities = [Training::class, Exercise::class, Serie::class, TrainingHistory::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TrainingDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun serieDao(): SerieDao
    abstract fun historyDao(): TrainingHistoryDao

    companion object {
        @Volatile
        private var Instance: TrainingDatabase? = null

        fun getDatabase(context: Context): TrainingDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    TrainingDatabase::class.java,
                    "training_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it}
            }
        }
    }
}