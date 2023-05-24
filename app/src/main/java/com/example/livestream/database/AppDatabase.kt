package com.example.livestream.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.livestream.models.Stream
import com.example.livestream.models.User

@Database(entities = [Stream::class, User::class], version = 5)
abstract class AppDatabase : RoomDatabase() {

    abstract fun streamDao(): StreamDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getAppDatabase(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        @Suppress("unused")
        fun destroyInstance() {
            instance?.close()
            instance = null
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "livestream.db"
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}
