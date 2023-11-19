package com.example.weatherapplication.data.network

import com.example.weatherapplication.data.model.WeatherEightHourResponse
import com.example.weatherapplication.data.model.WeatherOneDayResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    fun getOneDayWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Single<WeatherOneDayResponse>

    @GET("data/2.5/forecast")
    fun getEightHourWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Single<WeatherEightHourResponse>
}