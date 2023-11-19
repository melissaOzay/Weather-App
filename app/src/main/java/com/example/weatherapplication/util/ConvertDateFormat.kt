package com.example.weatherapplication.util

import java.text.SimpleDateFormat
class ConvertDateFormat{
    companion object{
        fun parseDateFormat(date :String):String{
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val incomingDate= dateFormat.parse(date)
            val timeFormat = SimpleDateFormat("HH:mm:ss")
            return timeFormat.format(incomingDate)
        }
    }
}

