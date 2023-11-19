package com.example.weatherapplication.data.repository

import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.data.model.WeatherEightHourResponse
import com.example.weatherapplication.data.model.WeatherOneDayResponse
import com.example.weatherapplication.data.network.WeatherAPI
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApiService: WeatherAPI) :
    WeatherRepository {

    override fun getOneDayWeather(lat: Double, lon: Double): Single<WeatherOneDayResponse> {
        return weatherApiService.getOneDayWeather(lat, lon, BuildConfig.API_KEY)
    }

    override fun getEightHourWeather(
        lat: Double,
        lon: Double
    ): Single<WeatherEightHourResponse> {
        return weatherApiService.getEightHourWeather(lat, lon, BuildConfig.API_KEY)
    }

}