package com.example.mapbox2.domain.repository

import android.content.Context
import com.example.mapbox2.data.models.LocationData
import com.example.mapbox2.data.local.LocationDatabase
import com.example.mapbox2.data.models.ResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MainRepository {

    suspend fun getLastLocation(): Flow<ResultData<LocationData>>


    suspend fun addLocation(longitude: Double, latitude: Double)
}