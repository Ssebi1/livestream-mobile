package com.example.livestream.ui.di.modules

import android.app.Application
import com.example.livestream.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The database module for injecting the [AppDatabase] and every DAO.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(application: Application) = AppDatabase.getAppDatabase(application)

    @Singleton
    @Provides
    fun providesStreamDao(appDatabase: AppDatabase) = appDatabase.streamDao()

    @Singleton
    @Provides
    fun providesUserDao(appDatabase: AppDatabase) = appDatabase.userDao()
}