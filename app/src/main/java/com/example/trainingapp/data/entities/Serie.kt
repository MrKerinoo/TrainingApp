package com.example.trainingapp.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "series",
    foreignKeys = [
        ForeignKey(
        entity = Exercise::class,
        parentColumns = ["id"],
        childColumns = ["exerciseId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Serie (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val exerciseId: Int, // Foreign key
    val reps: Int,
    val weight: Double
)
