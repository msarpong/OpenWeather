package org.msarpong.openweather.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.msarpong.openweather.datamapping.WeatherResponse
import org.msarpong.openweather.service.WeatherReceiver
import org.msarpong.openweather.service.WeatherResult
import org.msarpong.openweather.service.WeatherService

sealed class MainEvent {
    object Load : MainEvent()
}

sealed class MainState {
    object InProgress : MainState()
    data class Success(val weatherList: WeatherResponse) : MainState()
    data class Error(val error: Throwable) : MainState()
}

class MainViewModel(private val weatherService: WeatherService) : ViewModel() {
    var state: MutableLiveData<MainState> = MutableLiveData()

    init {
        state.value = MainState.InProgress
    }

    fun send(event: MainEvent) {
        when (event) {
            is MainEvent.Load -> loadContent()
        }
    }

    private fun loadContent() {
        try {
            weatherService.getWeather(object : WeatherReceiver {
                override fun receive(result: WeatherResult) {
                    when (result) {
                        is WeatherResult.Success -> state.value =
                            MainState.Success(weatherList = result.weatherList)
                        is WeatherResult.Error -> state.value =
                            MainState.Error(error = result.error)
                    }
                }

            })
        } catch (exception: Throwable) {
            Log.d("MainViewModel", "loadContent()")
        }
    }
}