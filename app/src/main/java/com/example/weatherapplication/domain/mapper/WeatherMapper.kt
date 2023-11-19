package com.example.weatherapplication.domain.mapper

import com.example.weatherapplication.data.model.WeatherItem
import com.example.weatherapplication.data.model.WeatherOneDayResponse
import com.example.weatherapplication.domain.model.MainEntity
import com.example.weatherapplication.domain.model.MainOneDayEntity
import com.example.weatherapplication.domain.model.WeatherEightHourData
import com.example.weatherapplication.domain.model.WeatherEntity
import com.example.weatherapplication.domain.model.WeatherOneDayData
import com.example.weatherapplication.domain.model.WeatherOneDayEntity

fun WeatherOneDayResponse.toWeatherData() : WeatherOneDayData{
    return WeatherOneDayData(
      main = MainOneDayEntity(main.temp), weather =
        weather.map {
            WeatherOneDayEntity(it.main,it.description,it.icon)
        }
    )
}
fun WeatherItem.toWeatherDataList(): WeatherEightHourData {
    return WeatherEightHourData(
        dt = dt, main = MainEntity(main.temp), weather =
        weather.map {
            WeatherEntity(
                main = it.main,
                description = it.description,
                icon = it.icon
            )
        }, dt_txt = dt_txt,
        isSelected = false
    )
}
