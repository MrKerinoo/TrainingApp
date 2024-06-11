package com.example.trainingapp.data

import androidx.room.TypeConverter
import java.util.Date

/**
 * Converters is a class that contains the methods to convert the data types used in the database.
 */
class Converters {
    @TypeConverter
    fun fromTimetamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}