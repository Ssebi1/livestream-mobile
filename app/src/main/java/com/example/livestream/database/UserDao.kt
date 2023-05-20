package com.example.livestream.database

import androidx.room.Dao
import androidx.room.Query
import com.example.livestream.models.User

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM User")
    fun getAll(): List<User>?

    @Query("SELECT * FROM User WHERE loggedIn = 1")
    fun getCurrentlyLoggedUser(): User?

    @Query("UPDATE User SET loggedIn = 0 WHERE loggedIn = 1")
    suspend fun logoutUser(): Int
}