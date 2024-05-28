package com.example.trainingapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Training::class], version = 1, exportSchema = false)
abstract class TrainingDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao

    companion object {
        @Volatile
        private var Instance: TrainingDatabase? = null

        fun getDatabase(context: android.content.Context): TrainingDatabase {
            return Instance ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    TrainingDatabase::class.java,
                    "training_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                Instance = instance
                instance
            }
        }
    }
}