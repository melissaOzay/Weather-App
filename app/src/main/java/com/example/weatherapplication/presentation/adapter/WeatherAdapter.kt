package com.example.weatherapplication.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.ItemWeatherBinding
import com.example.weatherapplication.domain.model.WeatherEightHourData
import com.example.weatherapplication.presentation.adapter.`interface`.WeatherAdapterListener
import com.example.weatherapplication.util.ConvertDateFormat
import com.example.weatherapplication.util.WeatherIconMapper
import kotlin.properties.Delegates


class WeatherAdapter(private val listener: WeatherAdapterListener) :
    ListAdapter<WeatherEightHourData, WeatherAdapter.CompanyViewHolder>(WeatherDiffUtil()) {

    private lateinit var binding: ItemWeatherBinding

    class CompanyViewHolder(binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvDec = binding.tvDec
        private val ivWeather = binding.ivWeather
        private val tvHour = binding.tvHour
        private val cardView = binding.linear
        private lateinit var incomingDate: String
        private var tempToDegree by Delegates.notNull<Int>()
        var icon by Delegates.notNull<Int>()
        private lateinit var desc: String
        private val context: Context = itemView.context


        fun bindItems(item: WeatherEightHourData, listener: WeatherAdapterListener) {
            icon = WeatherIconMapper.getIcon(item.weather[0].icon)
            ivWeather.setImageResource(icon)
            val temp = item.main.temp
            tempToDegree = (temp - 273.15).toInt()
            "$tempToDegree°".let {
                tvDec.text = it
            }

            val date = item.dt_txt
            desc = item.weather[0].description.replaceFirstChar { it.uppercase() }
            incomingDate = ConvertDateFormat.parseDateFormat(date)
            tvHour.text = incomingDate
            itemView.setOnClickListener {
                listener.clickItem(
                    adapterPosition,
                    incomingDate,
                    tempToDegree,
                    icon,
                    desc
                )
            }
            if (item.isSelected) {
                cardView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.queen_blue
                    )
                )
            } else {
                cardView.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.medium_blue
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bindItems(getItem(position), listener)
    }

    private class WeatherDiffUtil : DiffUtil.ItemCallback<WeatherEightHourData>() {

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
            return oldItem == newItem
        }
    }


}