package com.example.myapplication.util

import com.example.myapplication.R

class WeatherIconMapper {
companion object{
    fun getIcon(icon:String):Int{
                return when (icon) {
                    "01n", "01d" -> R.drawable.ic_01d
                    "02n", "02d" -> R.drawable.ic_02d
                    "03n", "03d" -> R.drawable.ic_04d
                    "04n", "04d" -> R.drawable.ic_03d
                    "09n", "09d" -> R.drawable.ic_09d
                    "10n", "10d" -> R.drawable.ic_10d
                    "11n", "11d" -> R.drawable.ic_11d
                    "13n", "13d" -> R.drawable.ic_13d
                    "50n", "50d" -> R.drawable.ic_50d
                    else -> R.drawable.bg_weather

        }
    }
}
}