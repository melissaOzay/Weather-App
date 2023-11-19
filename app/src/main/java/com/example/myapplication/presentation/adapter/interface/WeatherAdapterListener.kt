package com.example.myapplication.presentation.adapter.`interface`

import java.text.FieldPosition


interface WeatherAdapterListener {
    fun clickItem(
        position: Int,
        hour: String, degree: Int, image: Int, desc: String
    )
}