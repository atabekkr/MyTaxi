package com.example.mapbox2.domain.usecase.impl

import com.example.mapbox2.data.models.LocationData
import com.example.mapbox2.data.models.ResultData
import com.example.mapbox2.domain.repository.MainRepository
import com.example.mapbox2.domain.usecase.MainUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainUseCaseImpl(private val repo: MainRepository): MainUseCase {

    override suspend fun addLocation(longitude: Double, latitude: Double) = repo.addLocation(longitude, latitude)

    override suspend fun getLastLocation() = repo.getLastLocation()
}