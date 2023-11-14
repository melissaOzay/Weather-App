package com.example.myapplication.domain

import com.example.myapplication.data.model.WeatherOneDayResponse
import com.example.myapplication.data.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherOneDayUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    fun loadData(lat: Double, lon: Double): Single<WeatherOneDayResponse> {
        return weatherRepository.getOneDayWeather(lat, lon)

    }
}