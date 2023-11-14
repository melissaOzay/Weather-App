package com.example.myapplication.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.WeatherEightHourUseCase
import com.example.myapplication.domain.WeatherOneDayUseCase
import com.example.myapplication.domain.mapper.toWeatherData
import com.example.myapplication.domain.model.WeatherEightHourData
import com.example.myapplication.domain.model.WeatherOneDayData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class WeatherVM @Inject constructor(
    private val weatherOneUseCase: WeatherOneDayUseCase,
    private val weatherFiveUseCase: WeatherEightHourUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _weatherOneDayData = MutableLiveData<WeatherOneDayData>()
    val weatherOneDayData: LiveData<WeatherOneDayData> get() = _weatherOneDayData

    private val _weatherEightHourData = MutableLiveData<WeatherEightHourData>()
    val weatherEightHourData: LiveData<WeatherEightHourData> get() = _weatherEightHourData

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _failure = MutableLiveData<String>()
    val failure: LiveData<String> get() = _failure

    init {
        _progress.value = true

    }

    fun getWeatherOneDay(lat: Double, lon: Double) {
        _progress.value = true
        compositeDisposable.add(
            weatherOneUseCase.loadData(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.toWeatherData() }
                .subscribe({ response ->
                    _progress.value = false
                    _weatherOneDayData.postValue(response)
                }, {
                    _failure.postValue(it.toString())
                })
        )
    }

    fun getEightHourWeather(lat: Double, lon: Double) {
        compositeDisposable.add(
            weatherFiveUseCase.loadData(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.toWeatherData() }
                .subscribe({ response ->
                    _weatherEightHourData.postValue(response)

                }, {
                    _failure.postValue(it.toString())
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}