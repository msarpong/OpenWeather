package org.msarpong.openweather.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.msarpong.openweather.utils.KeyValueStorage
import org.msarpong.openweather.utils.SETTING_NOTIFICATION
import org.msarpong.openweather.utils.SETTING_THEME
import org.msarpong.openweather.utils.SETTING_UNIT

sealed class SettingEvent {
    data class Notifications(val state: Boolean) : SettingEvent()
    data class Unit(val state: Boolean) : SettingEvent()
    data class DarkTheme(val state: Boolean) : SettingEvent()
}

sealed class SettingState {
    object NotificationsDisabled : SettingState()
    object NotificationsEnabled : SettingState()
    object DarkThemeDisabled : SettingState()
    object DarkThemeEnabled : SettingState()

}


class SettingViewModel(private val prefs: KeyValueStorage) : ViewModel() {

    fun send(event: SettingEvent) {
        when (event) {
            is SettingEvent.Unit -> saveUnit(event.state)
            is SettingEvent.Notifications -> saveNotification(event.state)
            is SettingEvent.DarkTheme -> saveUi(event.state)
        }
    }

    private fun saveUnit(event: Boolean) {
        if (event) {
            prefs.putBoolean(SETTING_UNIT, true)
        } else {
            prefs.putBoolean(SETTING_UNIT, false)
        }
    }

    private fun saveNotification(event: Boolean) {
        if (event) {
            prefs.putBoolean(SETTING_NOTIFICATION, true)
        } else {
            prefs.putBoolean(SETTING_NOTIFICATION, false)
        }
    }

    private fun saveUi(event: Boolean) {
        if (event) {
            prefs.putBoolean(SETTING_THEME, true)
        } else {
            prefs.putBoolean(SETTING_THEME, false)
        }
    }
}