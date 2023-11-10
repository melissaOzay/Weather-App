package com.example.myapplication.data.repository

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.model.EightHourWeatherEntity
import com.example.myapplication.data.model.WeatherEntity
import com.example.myapplication.data.network.WeatherAPI
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApiService: WeatherAPI) :
    WeatherRepository {

    override fun getOneDayWeather(lat: Double, lon: Double): Single<WeatherEntity.WeatherData> {
        return weatherApiService.getOneDayWeather(lat, lon, BuildConfig.API_NAME)
    }

    override fun getEightHourWeather(
        lat: Double,
        lon: Double
    ): Single<EightHourWeatherEntity.WeatherData> {
        return weatherApiService.getEightHourWeather(lat, lon, BuildConfig.API_NAME)
    }

}