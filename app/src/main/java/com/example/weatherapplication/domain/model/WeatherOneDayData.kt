package com.example.weatherapplication.domain.model


data class WeatherOneDayData(
    val main: MainOneDayEntity,
    val weather: List<WeatherOneDayEntity>
)

data class MainOneDayEntity(val temp: Double)

data class WeatherOneDayEntity(
    val main: String,
    val description: String,
    val icon: String
)