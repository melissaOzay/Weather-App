package com.example.weatherapplication.data.repository

import com.example.weatherapplication.data.model.WeatherOneDayResponse
import com.example.weatherapplication.data.model.WeatherEightHourResponse
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getOneDayWeather(lat: Double, lon: Double): Single<WeatherOneDayResponse>
    fun getEightHourWeather(lat: Double, lon: Double): Single<WeatherEightHourResponse>
}