package com.example.mapbox2.di

import com.example.mapbox2.domain.usecase.MainUseCase
import com.example.mapbox2.domain.usecase.impl.MainUseCaseImpl
import org.koin.dsl.module

val domainModule = module {

    factory<MainUseCase> {
        MainUseCaseImpl(repo = get())
    }
}