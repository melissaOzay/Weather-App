package com.example.weatherapplication.domain

import com.example.weatherapplication.data.model.WeatherOneDayResponse
import com.example.weatherapplication.data.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherOneDayUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    fun loadData(lat: Double, lon: Double): Single<WeatherOneDayResponse> {
        return weatherRepository.getOneDayWeather(lat, lon)

    }
}