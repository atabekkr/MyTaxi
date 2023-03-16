package com.example.mapbox2.presentation

import com.example.mapbox2.data.models.LocationData
import kotlinx.coroutines.flow.*

interface MainViewModel{

    val getLastLocationFlow: Flow<LocationData>
    val messageFlow: Flow<String>
    val errorFlow: Flow<Throwable>

    suspend fun getLastLocation()

    suspend fun addLocation(longitude: Double, latitude: Double)
}