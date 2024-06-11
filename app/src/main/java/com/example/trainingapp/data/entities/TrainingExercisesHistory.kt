package com.example.trainingapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * TrainingExercisesHistory is a data class that represents the training exercises history entity in the database.
 */
data class TrainingExercisesHistory (
    @Embedded val training: TrainingHistory,
    @Relation(
        parentColumn = "id",
        entityColumn = "trainingId"
    )
    val exercises: List<ExerciseHistory>
)