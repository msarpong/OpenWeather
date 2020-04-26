package org.msarpong.openweather.ui.setting

import androidx.lifecycle.ViewModel
import org.msarpong.openweather.utils.KeyValueStorage
import org.msarpong.openweather.utils.NOTIFICATION
import org.msarpong.openweather.utils.UNIT_TEMP

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
            is SettingEvent.DarkTheme -> saveUnit(event.state)
        }
    }

    private fun saveUnit(event: Boolean) {
        if (event) {
            prefs.putBoolean(UNIT_TEMP, true)
        } else {
            prefs.putBoolean(UNIT_TEMP, false)
        }
    }

    private fun saveNotification(event: Boolean) {
        if (event) {
            prefs.putBoolean(NOTIFICATION, true)
        } else {
            prefs.putBoolean(NOTIFICATION, false)
        }
    }
}