package com.example.weatherapplication.presentation.adapter.`interface`


interface WeatherAdapterListener {
    fun clickItem(
        position: Int,
        hour: String, degree: Int, image: Int, desc: String
    )
}