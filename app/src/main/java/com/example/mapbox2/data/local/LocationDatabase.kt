package com.example.mapbox2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mapbox2.data.local.dao.LocationDao
import com.example.mapbox2.data.models.LocationData


@Database(entities = [LocationData::class], version = 2, exportSchema = true)
abstract class LocationDatabase : RoomDatabase()  {

    companion object {
        private var instance: LocationDatabase? = null

        fun getInstance(context: Context): LocationDatabase {
            instance?.let {
                return it
            }

            val db = Room.databaseBuilder(context, LocationDatabase::class.java, "longlat.db")
                .allowMainThreadQueries()
                .build()

            instance = db
            return db
        }
    }

    abstract fun getLongLatDao(): LocationDao
}