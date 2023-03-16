package com.example.mapbox2.domain.usecase

import com.example.mapbox2.data.models.LocationData
import com.example.mapbox2.data.models.ResultData
import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    suspend fun addLocation(longitude: Double,latitude:Double)
    suspend fun getLastLocation(): Flow<ResultData<LocationData>>
}