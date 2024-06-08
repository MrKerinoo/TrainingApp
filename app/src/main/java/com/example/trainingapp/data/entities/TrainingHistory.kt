package com.example.trainingapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.trainingapp.data.Converters
import java.util.Date

@Entity(tableName = "history")
data class TrainingHistory (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @TypeConverters(Converters::class)
    val date: Date,
    @TypeConverters(Converters::class)
    val exercises: List<Exercise>
)