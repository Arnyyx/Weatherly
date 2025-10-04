package com.arny.weatherly.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arny.weatherly.data.local.dao.LocationDao
import com.arny.weatherly.data.local.dao.WeatherDao
import com.arny.weatherly.data.local.entity.LocationEntity
import com.arny.weatherly.data.local.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class, LocationEntity::class],
    exportSchema = false, version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun locationDao(): LocationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "weather_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}