package com.example.livestream.database

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArray(ts: List<T>)

    @Update
    suspend fun update(t: T)

    @Update
    suspend fun updateArray(ts: List<T>)

    @Delete
    suspend fun delete(t: T)

    @Delete
    suspend fun deleteArray(ts: List<T>)
}