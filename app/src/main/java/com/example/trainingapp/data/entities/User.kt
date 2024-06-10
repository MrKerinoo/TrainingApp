package com.example.trainingapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    val lang: String,
    val darkMode: Boolean = true
)