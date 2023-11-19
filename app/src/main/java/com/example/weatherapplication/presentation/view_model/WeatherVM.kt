package com.example.weatherapplication.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.domain.WeatherEightHourUseCase
import com.example.weatherapplication.domain.WeatherOneDayUseCase
import com.example.weatherapplication.domain.mapper.toWeatherData
import com.example.weatherapplication.domain.mapper.toWeatherDataList
import com.example.weatherapplication.domain.model.WeatherEightHourData
import com.example.weatherapplication.domain.model.WeatherOneDayData
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

    private val _weatherEightHourData = MutableLiveData<List<WeatherEightHourData>>()
    val weatherEightHourData: LiveData<List<WeatherEightHourData>> get() = _weatherEightHourData

    private val newItems = arrayListOf<WeatherEightHourData>()

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
                    _weatherOneDayData.value = response
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
                .map { it ->
                    it.list.map { it.toWeatherDataList() }
                }
                .subscribe({ response ->
                    newItems.addAll(response.take(8))
                    _weatherEightHourData.value = response.take(8)

                }, {
                    _failure.postValue(it.toString())
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun selectedItem(position: Int) {
        newItems.filter {
            it.isSelected
        }.apply {
            this.map {
                it.isSelected = false
            }
        }
        if (position != -1) {
            newItems[position].isSelected = true
        }
        _weatherEightHourData.value = newItems.toMutableList()

    }

}