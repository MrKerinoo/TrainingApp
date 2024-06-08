package com.example.trainingapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TrainingExercises (
    @Embedded val training: Training,
    @Relation(
        parentColumn = "id",
        entityColumn = "trainingId"
    )
    val exercises: List<Exercise>
)