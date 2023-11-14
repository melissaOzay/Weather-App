package com.example.myapplication.domain.mapper

import com.example.myapplication.data.model.WeatherResponse
import com.example.myapplication.data.model.WeatherOneDayResponse
import com.example.myapplication.domain.model.WeatherEightHourData
import com.example.myapplication.domain.model.WeatherOneDayData

fun WeatherOneDayResponse.toWeatherData() = WeatherOneDayData(
    weather = weather,
    main = main

)
fun WeatherResponse.toWeatherData() = WeatherEightHourData(
    list = list
)