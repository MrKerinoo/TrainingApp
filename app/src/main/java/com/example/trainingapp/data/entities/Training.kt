package com.example.trainingapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.trainingapp.data.Converters
import java.util.Date

/**
 * Training is a data class that represents the training entity in the database.
 */
@Entity(tableName = "trainings")
data class Training (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @TypeConverters(Converters::class)
    val date: Date?
)