package com.example.myapplication.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.EightHourWeatherEntity
import com.example.myapplication.data.model.WeatherEntity
import com.example.myapplication.domain.WeatherEightHourUseCase
import com.example.myapplication.domain.WeatherOneDayUseCase
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

    private val _weatherOneDayData = MutableLiveData<WeatherEntity.WeatherData>()
    val weatherOneDayData: LiveData<WeatherEntity.WeatherData> get() = _weatherOneDayData

    private val _weatherEightHourData = MutableLiveData<EightHourWeatherEntity.WeatherData>()
    val weatherEightHourData: LiveData<EightHourWeatherEntity.WeatherData> get() = _weatherEightHourData

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