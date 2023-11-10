package com.example.myapplication.data.network

import com.example.myapplication.data.model.EightHourWeatherEntity
import com.example.myapplication.data.model.WeatherEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    fun getOneDayWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Single<WeatherEntity.WeatherData>

    @GET("data/2.5/forecast")
    fun getEightHourWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Single<EightHourWeatherEntity.WeatherData>
}