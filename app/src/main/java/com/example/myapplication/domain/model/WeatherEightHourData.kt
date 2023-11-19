package com.example.myapplication.domain.model

data class WeatherEightHourData(
    val dt: Long,
    val main: MainEntity,
    val weather: List<WeatherEntity>,
    val dt_txt: String,
    var isSelected: Boolean = false
)

data class MainEntity(val temp: Double)

data class WeatherEntity(
    val main: String,
    val description: String,
    val icon: String
)
