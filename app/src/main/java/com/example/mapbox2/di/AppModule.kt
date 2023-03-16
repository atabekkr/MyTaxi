package com.example.mapbox2.di

import com.example.mapbox2.presentation.MainViewModel
import com.example.mapbox2.presentation.impl.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        MainViewModelImpl(useCase = get())
    }
}