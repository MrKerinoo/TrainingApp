package com.example.trainingapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class TrainingExercisesHistory (
    @Embedded val training: TrainingHistory,
    @Relation(
        parentColumn = "id",
        entityColumn = "trainingId"
    )
    val exercises: List<ExerciseHistory>
)