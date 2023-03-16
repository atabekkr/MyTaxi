package com.example.mapbox2.data.local.dao

import androidx.room.*
import com.example.mapbox2.data.models.LocationData


@Dao
interface LocationDao {

    @Insert
    suspend fun addLocation(latLocationData: LocationData)

    @Query("SELECT * FROM locations")
    suspend fun getAllLocations(): List<LocationData>
//
//    @Query("SELECT * FROM locations")
//    suspend fun getLastLocation(): LocationData
}