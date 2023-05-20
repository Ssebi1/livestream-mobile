package com.example.livestream.ui.di.modules

import android.app.Application
import com.example.livestream.request.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * API Client module for injecting the [ApiClient] and *ApiService*.
 */
@Module
@InstallIn(SingletonComponent::class)
class ApiClientModule {

    @Provides
    @Singleton
    fun provideApiClient(application: Application) = ApiClient.getInstance(application)

    @Provides
    @Singleton
    fun provideApiService(apiClient: ApiClient) = apiClient.apiService
}