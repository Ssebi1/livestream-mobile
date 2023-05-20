package com.example.livestream.api

import com.example.livestream.models.User
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {

    @GET("/users/streamers")
    fun getStreamers(): Response<List<User>>

    @POST("users/login")
    @FormUrlEncoded
    suspend fun login(@FieldMap params: HashMap<String, Any?>): User
}