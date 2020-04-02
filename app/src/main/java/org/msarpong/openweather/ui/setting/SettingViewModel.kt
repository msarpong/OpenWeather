package org.msarpong.openweather.ui.setting

import androidx.lifecycle.ViewModel
import org.msarpong.openweather.datamapping.WeatherResponse

sealed class SettingEvent {
    object Load : SettingEvent()
}

sealed class SettingState {
    object InProgress : SettingState()
    data class Success(val weatherList: WeatherResponse) : SettingState()
    data class Error(val error: Throwable) : SettingState()
}

class SettingViewModel : ViewModel() {

}