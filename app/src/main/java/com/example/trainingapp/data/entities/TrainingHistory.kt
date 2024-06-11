package com.example.trainingapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.trainingapp.data.Converters
import java.util.Date

@Entity(tableName = "training_history")
data class TrainingHistory (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val time: Int,
    @TypeConverters(Converters::class)
    val date: Date = Date()
)