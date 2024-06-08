package com.example.trainingapp.data

import androidx.room.TypeConverter
import com.example.trainingapp.data.entities.Exercise
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimetamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromExerciseList(exercises: List<Exercise>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Exercise>>() {}.type
        return gson.toJson(exercises, type)
    }

    @TypeConverter
    fun toExerciseList(exercisesString: String): List<Exercise> {
        val gson = Gson()
        val type = object : TypeToken<List<Exercise>>() {}.type
        return gson.fromJson(exercisesString, type)
    }
}