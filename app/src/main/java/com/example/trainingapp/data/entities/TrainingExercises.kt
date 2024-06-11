package com.example.trainingapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * TrainingExercisesHistory is a data class that represents the training exercises history entity in the database.
 */
data class TrainingExercises (
    @Embedded val training: Training,
    @Relation(
        parentColumn = "id",
        entityColumn = "trainingId"
    )
    val exercises: List<Exercise>
)