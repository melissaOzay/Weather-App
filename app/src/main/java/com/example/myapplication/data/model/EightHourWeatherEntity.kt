package com.example.myapplication.data.model

class EightHourWeatherEntity {
    data class WeatherData(
        val cod: String,
        val message: Int,
        val cnt: Int,
        val list: List<WeatherItem>,
        val city: City
    )

    data class WeatherItem(
        val dt: Long,
        val main: MainInfo,
        val weather: List<WeatherInfo>,
        val clouds: CloudsInfo,
        val wind: WindInfo,
        val visibility: Int,
        val pop: Double,
        val rain: RainInfo?,
        val sys: SysInfo,
        val dt_txt: String
    )

    data class MainInfo(
        val temp: Double,
        val feels_like: Double,
        val temp_min: Double,
        val temp_max: Double,
        val pressure: Int,
        val sea_level: Int,
        val grnd_level: Int,
        val humidity: Int,
        val temp_kf: Double
    )

    data class WeatherInfo(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    data class CloudsInfo(
        val all: Int
    )

    data class WindInfo(
        val speed: Double,
        val deg: Int,
        val gust: Double
    )

    data class RainInfo(
        val `3h`: Double
    )

    data class SysInfo(
        val pod: String
    )

    data class City(
        val id: Int,
        val name: String,
        val coord: Coord,
        val country: String,
        val population: Int,
        val timezone: Int,
        val sunrise: Long,
        val sunset: Long
    )

    data class Coord(
        val lat: Double,
        val lon: Double
    )

}