package com.example.myapplication.data.repository

import com.example.myapplication.data.model.EightHourWeatherEntity
import com.example.myapplication.data.model.WeatherEntity
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getOneDayWeather(lat: Double, lon: Double): Single<WeatherEntity.WeatherData>
    fun getEightHourWeather(lat: Double, lon: Double): Single<EightHourWeatherEntity.WeatherData>
}