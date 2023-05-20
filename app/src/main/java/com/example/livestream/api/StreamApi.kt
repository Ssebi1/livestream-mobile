package com.example.livestream.api

import com.example.livestream.models.Stream
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StreamApi {

    @GET("/streams")
    fun getStreams(): Response<List<Stream>>

    @GET("/streams/{id}")
    fun getStream(
        @Path("id") streamId:String
    ): Response<Stream>
}