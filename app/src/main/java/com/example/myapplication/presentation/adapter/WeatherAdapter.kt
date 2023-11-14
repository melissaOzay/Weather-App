package com.example.myapplication.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemWeatherBinding
import com.example.myapplication.domain.model.WeatherEightHourData
import com.example.myapplication.presentation.adapter.*
import com.example.myapplication.presentation.adapter.`interface`.WeatherAdapterListener
import com.example.myapplication.util.ConvertDateFormat
import com.example.myapplication.util.WeatherIconMapper
import kotlin.properties.Delegates


class WeatherAdapter(private val listener: WeatherAdapterListener) :
    RecyclerView.Adapter<WeatherAdapter.CompanyViewHolder>() {

    private var items = WeatherEightHourData(emptyList())
    private lateinit var binding: ItemWeatherBinding
     var row_index = -1
    var clickButton=false

    fun setListData(items: WeatherEightHourData) {
        this.items = items
    }

    class CompanyViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val name = binding.tvDec
        private val iv = binding.ivWeather
        private val hour = binding.tvHour
        val cardView = binding.linear
        lateinit var incomingDate: String
        var tempToDegree by Delegates.notNull<Int>()
        var icon by Delegates.notNull<Int>()
        lateinit var desc: String

        @SuppressLint("SetTextI18n")
        fun bindItems(item: WeatherEightHourData, position: Int) {
            icon = WeatherIconMapper.getIcon(item.list[position].weather[0].icon)
            iv.setImageResource(icon)
            val temp = item.list[position].main.temp
            tempToDegree = (temp - 273.15).toInt()
            name.text = "$tempToDegree°"

            val date = item.list[position].dt_txt
            desc = item.list[position].weather[0].description.replaceFirstChar { it.uppercase() }
            incomingDate = ConvertDateFormat.parseDateFormat(date)
            hour.text = incomingDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        if (position < 8) {
            holder.bindItems(items,position)
            holder.itemView.setOnClickListener {
                row_index = position
                listener.clickItem(
                    holder.incomingDate,
                    holder.tempToDegree,
                    holder.icon,
                    holder.desc
                )
                notifyDataSetChanged()
            }
            if (row_index == position) {
                holder.cardView.setBackgroundColor(Color.parseColor("#466B9E"))
            } else {
                holder.cardView.setBackgroundColor(Color.parseColor("#BCE8FF"))
            }
        }
    }

    override fun getItemCount(): Int {
        return minOf(items.list.size, 8)
    }
}