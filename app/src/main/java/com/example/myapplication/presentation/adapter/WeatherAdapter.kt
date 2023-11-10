package com.example.myapplication.presentation.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.model.EightHourWeatherEntity
import com.example.myapplication.databinding.ItemWeatherBinding
import com.example.myapplication.presentation.adapter.*
import com.example.myapplication.presentation.adapter.`interface`.WeatherAdapterListener
import com.example.myapplication.util.ConvertDateFormat
import com.example.myapplication.util.WeatherIconMapper


class WeatherAdapter(private val listener: WeatherAdapterListener) :
    RecyclerView.Adapter<WeatherAdapter.CompanyViewHolder>() {

    private var items = ArrayList<EightHourWeatherEntity.WeatherItem>()
    private lateinit var binding: ItemWeatherBinding
    fun setListData(items: List<EightHourWeatherEntity.WeatherItem>) {
        this.items = ArrayList(items)
    }

    class CompanyViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val name = binding.tvDec
        private val iv = binding.ivWeather
        private val hour = binding.tvHour///
        var cardView = binding.cardView//

        @SuppressLint("SetTextI18n")
        fun bindItems(item: EightHourWeatherEntity.WeatherItem, listener: WeatherAdapterListener) {
            val icon: Int = WeatherIconMapper.getIcon(item.weather[0].icon)
            iv.setImageResource(icon)
            val temp = item.main.temp
            val tempToDegree = (temp - 273.15).toInt()
            name.text = "$tempToDegree°"

            val date = item.dt_txt
            val incomingDate = ConvertDateFormat.parseDateFormat(date)
            hour.text = incomingDate
            itemView.setOnClickListener {
                listener.clickItem(incomingDate, tempToDegree, icon)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        if (position < 8) {
            holder.bindItems(items[position], listener)
//

        }
    }

    override fun getItemCount(): Int {
        return minOf(items.size, 8)
    }
}