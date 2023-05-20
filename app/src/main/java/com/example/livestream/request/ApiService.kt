package com.example.livestream.request

import com.example.livestream.models.Stream
import com.example.livestream.models.User
import retrofit2.http.*

interface ApiService {
    @GET("streams")
    suspend fun getStreams(): List<Stream>

    @GET("streams/{id}")
    suspend fun getStream(streamId: String): Stream

    @GET("users/streamers")
    suspend fun getStreamers(): List<User>

    @POST("users/login")
    @FormUrlEncoded
    suspend fun login(@FieldMap params: HashMap<String, Any?>): User
}