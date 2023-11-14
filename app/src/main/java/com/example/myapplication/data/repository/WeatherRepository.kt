package com.example.myapplication.data.repository

import com.example.myapplication.data.model.WeatherOneDayResponse
import com.example.myapplication.data.model.WeatherResponse
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getOneDayWeather(lat: Double, lon: Double): Single<WeatherOneDayResponse>
    fun getEightHourWeather(lat: Double, lon: Double): Single<WeatherResponse>
}