package com.example.trainingapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trainingapp.data.daos.TrainingDao
import com.example.trainingapp.data.entities.Training

@Database(entities = [Training::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TrainingDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao

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