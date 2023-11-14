package com.example.myapplication.domain.model

import com.example.myapplication.data.model.Weather
import com.example.myapplication.data.model.Main

data class WeatherOneDayData(
    val weather: List<Weather>,
    val main: Main)
