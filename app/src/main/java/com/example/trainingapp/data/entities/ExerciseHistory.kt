package com.example.trainingapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.trainingapp.data.Converters
import java.util.Date

@Entity(
    tableName = "exercises_history",
    foreignKeys = [
        ForeignKey(
            entity = TrainingHistory::class,
            parentColumns = ["id"],
            childColumns = ["trainingId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val trainingId: Int, // Foreign key
    val name: String,
    val sets: Int,
    val reps: Int,
    val weight: Float,
    @TypeConverters(Converters::class)
    val dateAdded: Date = Date()
)