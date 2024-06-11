package com.example.trainingapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User is a data class that represents the user entity in the database.
 */
@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    val lang: Int,
    val darkMode: Boolean = true
)