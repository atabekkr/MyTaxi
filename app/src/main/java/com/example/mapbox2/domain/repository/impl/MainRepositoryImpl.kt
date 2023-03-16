package com.example.mapbox2.domain.repository.impl

import android.util.Log
import com.example.mapbox2.data.local.dao.LocationDao
import com.example.mapbox2.data.models.LocationData
import com.example.mapbox2.data.models.ResultData
import com.example.mapbox2.domain.repository.MainRepository
import kotlinx.coroutines.flow.flow

class MainRepositoryImpl(private val dao: LocationDao) : MainRepository {

    override suspend fun getLastLocation() = flow {
        if (dao.getAllLocations().isNotEmpty()) {
            emit(ResultData.Success(dao.getAllLocations().last()))
        } else {
            emit(ResultData.Message("Wait a bit, we need to find your location, and press button with location icon"))
        }
    }

    override suspend fun addLocation(longitude: Double, latitude: Double) {
        Log.e("TTT", "$latitude, $longitude")
        dao.addLocation(LocationData(0, longitude, latitude))
    }

}