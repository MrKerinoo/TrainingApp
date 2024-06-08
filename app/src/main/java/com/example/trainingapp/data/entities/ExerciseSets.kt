package com.example.trainingapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseSets (
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseId"
    )
    val sets: List<Set>
)
