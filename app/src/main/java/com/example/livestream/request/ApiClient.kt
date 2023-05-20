package com.example.livestream.request

import android.app.Application
import com.example.livestream.database.AppDatabase
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient private constructor(private val application: Application) {

    companion object {
        private const val BASE_URL = "https://leven-tv.com/api/"
        private var instance: ApiClient? = null

        fun getInstance(application: Application) = instance ?: synchronized(this) {
            instance ?: ApiClient(application).also { instance = it }
        }

        private val UNRESTRICTED = arrayOf(
            "/streams" to "GET",
            "/users/streamers" to "GET"
        )
    }

    private val appDatabase = AppDatabase.getAppDatabase(application)
    private val okHttpClient = createOkHttpClient()

    val retrofit: Retrofit by lazy {
        val gson = GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun cancelAllRequests() = okHttpClient.dispatcher.cancelAll()

    private fun Request.isRestricted() = UNRESTRICTED.all {
        it.first != this.url.encodedPath || it.second != method
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .cache(null)
            .build()
    }

}