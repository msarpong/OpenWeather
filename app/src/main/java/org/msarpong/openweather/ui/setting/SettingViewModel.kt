package org.msarpong.openweather.ui.setting

import androidx.lifecycle.ViewModel

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


class SettingViewModel() : ViewModel() {

    fun send(event: SettingEvent) {
        when (event) {
            is SettingEvent.Unit -> saveUnit(event.state)
            is SettingEvent.Notifications -> saveUnit(event.state)
            is SettingEvent.DarkTheme -> saveUnit(event.state)
        }
    }

    private fun saveUnit(event: Boolean) {
        if (event) {

        }
    }

    private fun saveAnimations(event: Boolean) {

    }
}