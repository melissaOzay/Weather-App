package com.example.weatherapplication.domain

import com.example.weatherapplication.data.model.WeatherEightHourResponse
import com.example.weatherapplication.data.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherEightHourUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    fun loadData(lat: Double, lon: Double): Single<WeatherEightHourResponse> {
        return weatherRepository.getEightHourWeather(lat, lon)
    }

}