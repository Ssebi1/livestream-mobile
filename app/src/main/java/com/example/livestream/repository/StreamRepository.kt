package com.example.livestream.repository

import com.example.livestream.database.StreamDao
import com.example.livestream.models.Stream
import com.example.livestream.request.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StreamRepository @Inject constructor(
    private val streamDao: StreamDao,
    private val apiService: ApiService
) {

    suspend fun getStreams() = apiService.getStreams()

    fun getStream(id: String) = streamDao.getStream(id)

    suspend fun saveStreams(streams: List<Stream>) = streamDao.insertArray(streams)
}