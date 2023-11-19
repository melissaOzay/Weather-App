package com.example.myapplication.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.domain.model.WeatherEightHourData

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
