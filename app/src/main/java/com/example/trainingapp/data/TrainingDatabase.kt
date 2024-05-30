package com.example.trainingapp.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Training::class], version = 1, exportSchema = false)
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