package com.example.livestream.database

import androidx.room.Dao
import com.example.livestream.models.Stream
import androidx.room.Query

@Dao
interface StreamDao : BaseDao<Stream> {

    @Query("SELECT * FROM Stream")
    fun getAll(): List<Stream>?

    @Query("SELECT * FROM Stream WHERE id= :id")
    fun getStream(id: String): Stream?
}