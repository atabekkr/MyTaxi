package com.example.mapbox2.presentation.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapbox2.data.models.LocationData
import com.example.mapbox2.data.models.ResultData
import com.example.mapbox2.domain.usecase.MainUseCase
import kotlinx.coroutines.flow.*

class MainViewModelImpl(private val useCase: MainUseCase):  ViewModel() {
     private val _getLastLocationFlow = MutableSharedFlow<LocationData>()
     val messageFlow = MutableSharedFlow<String>()
     val errorFlow = MutableSharedFlow<Throwable>()


    val getLastLocationFlow: Flow<LocationData> get() = _getLastLocationFlow
     suspend fun addLocation(longitude: Double, latitude: Double) {
        useCase.addLocation(longitude, latitude)
    }

     suspend fun getLastLocation() {
        useCase.getLastLocation().onEach {
            when (it) {
                is ResultData.Success -> {
                    _getLastLocationFlow.emit(it.data)
                }
                is ResultData.Message -> {
                    messageFlow.emit(it.message)
                }
                is ResultData.Error -> {
                    errorFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}