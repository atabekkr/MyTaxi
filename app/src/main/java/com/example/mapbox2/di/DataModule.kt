package com.example.mapbox2.di

import android.app.Application
import androidx.room.Room
import com.example.mapbox2.data.local.LocationDatabase
import com.example.mapbox2.data.local.dao.LocationDao
import com.example.mapbox2.domain.repository.MainRepository
import com.example.mapbox2.domain.repository.impl.MainRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single<MainRepository> {
        MainRepositoryImpl(dao = get())
    }


    fun provideDataBase(application: Application) : LocationDatabase {
        return Room.databaseBuilder(application, LocationDatabase::class.java, "longlat.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: LocationDatabase) : LocationDao {
        return dataBase.getLongLatDao()
    }

    single {
        provideDataBase(application = androidApplication())
    }

    single {
        provideDao(dataBase = get())
    }
}