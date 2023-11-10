package com.example.myapplication.domain

import com.example.myapplication.data.model.EightHourWeatherEntity
import com.example.myapplication.data.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherEightHourUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    fun loadData(lat: Double, lon: Double): Single<EightHourWeatherEntity.WeatherData> {
        return weatherRepository.getEightHourWeather(lat, lon)
    }

}