<h1 align="center">🎈 WeatherApp</h1></br>
I developed an app that shows the weather conditions during the day. After creating my own Api key on https://openweathermap.org/api, I added the daily and 8-hour weather forecast service to my project. 
When requesting these services,

***it asks me for Api key,***

```ruby
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            buildTypes.each {
                it.buildConfigField 'String', 'API_KEY', '"075c20ed0714a2323e3ccce70890497f"'
                it.resValue 'string', 'API_KEY', '"075c20ed0714a2323e3ccce70890497f"'
            }
        }

    }
```

```ruby
    override fun getOneDayWeather(lat: Double, lon: Double): Single<WeatherOneDayResponse> {
        return weatherApiService.getOneDayWeather(lat, lon, BuildConfig.API_KEY)
    }

    override fun getEightHourWeather(
        lat: Double,
        lon: Double
    ): Single<WeatherEightHourResponse> {
        return weatherApiService.getEightHourWeather(lat, lon, BuildConfig.API_KEY)
    }
```

***latitude and longitude,***

*(WeatherFragment)*
```ruby
    override fun onLocationChanged(location: Location) {
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geoCoder.getFromLocation(location.latitude, location.longitude, 1)

        val cityName = address?.get(0)?.adminArea
        binding.tvCity.text = cityName
        with(viewModel) {
            getWeatherOneDay(location.latitude, location.longitude)
            getEightHourWeather(location.latitude, location.longitude)
        }


    }
```
 we asked the user for permission for the location and sent this information. In this way, I can show the user both the daily average and the weather conditions for certain hours during the day.



## My Use Technologies

1) [MVVM](https://www.geeksforgeeks.org/android-build-a-movie-app-using-retrofit-and-mvvm-architecture-with-kotlin/),<br/>
2) [RxJava](https://bugrayetkinn.medium.com/android-rxjava-68317d0fd70d),<br/>
3) [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android),<br/>
4) [OkHttp and Retrofit](https://www.androidhire.com/retrofit-tutorial-in-kotlin/),<br/>
5) [ssp and sdp](https://medium.com/developer-student-clubs-iiit-allahabad/making-responsive-android-xml-designs-with-scalable-dp-sp-699536408052),<br/>
6) [Leak Canary](https://square.github.io/leakcanary/),<br/>
7) [Navigation Component](https://developer.android.com/guide/navigation/get-started)<br/>

## 📷 Pictures related to the application are located below
<a href="#"><img width="100%" height="auto" src="https://github.com/melissaOzay/Weather-App/assets/106618067/d9eb4a27-4efe-4118-8d28-f0dd15f3a326" height="400px"/></a>

