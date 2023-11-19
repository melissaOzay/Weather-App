package com.example.weatherapplication.data.di

import com.example.weatherapplication.data.repository.WeatherRepository
import com.example.weatherapplication.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun weatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

}