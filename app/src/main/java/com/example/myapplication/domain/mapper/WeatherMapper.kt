package com.example.myapplication.domain.mapper

import com.example.myapplication.data.model.WeatherItem
import com.example.myapplication.data.model.WeatherOneDayResponse
import com.example.myapplication.domain.model.MainEntity
import com.example.myapplication.domain.model.MainOneDayEntity
import com.example.myapplication.domain.model.WeatherEightHourData
import com.example.myapplication.domain.model.WeatherEntity
import com.example.myapplication.domain.model.WeatherOneDayData
import com.example.myapplication.domain.model.WeatherOneDayEntity

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
