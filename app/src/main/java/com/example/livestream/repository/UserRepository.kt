package com.example.livestream.repository

import com.example.livestream.database.StreamDao
import com.example.livestream.database.UserDao
import com.example.livestream.models.User
import com.example.livestream.request.ApiService
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiService: ApiService
) {

    suspend fun getStreamers() = apiService.getStreamers()

    suspend fun login(params: HashMap<String, Any?>) = apiService.login(params)

    suspend fun saveUser(user: User) = userDao.insert(user)

    suspend fun getCurrentyleLoggedUser() = userDao.getCurrentlyLoggedUser()

    suspend fun logoutUser() = userDao.logoutUser()

    suspend fun uploadImage(body: RequestBody) = apiService.uploadImage(body)
}