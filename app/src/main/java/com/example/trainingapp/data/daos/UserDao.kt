package com.example.trainingapp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.trainingapp.data.entities.User
import kotlinx.coroutines.flow.Flow

/**
 * UserDao is a Data Access Object interface that contains methods to interact with the user table in the database.
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): Flow<User>

    @Insert
    suspend fun insertUser(profile: User)

    @Update
    suspend fun updateUser(profile: User)

    @Delete
    suspend fun deletUser(profile: User)
}