package com.example.weatherapplication.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherapplication.domain.model.WeatherEightHourData

object WeatherDiffUtil : DiffUtil.ItemCallback<WeatherEightHourData>() {

    override fun areContentsTheSame(
        oldItem: WeatherEightHourData,
        newItem: WeatherEightHourData
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: WeatherEightHourData,
        newItem: WeatherEightHourData
    ): Boolean {
        return oldItem.dt == newItem.dt
    }
}
