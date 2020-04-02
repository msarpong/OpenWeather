package org.msarpong.openweather.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class SplashState {

}

sealed class SplashEvent {
    object Load : SplashEvent()
}

class SplashViewModel : ViewModel() {
    var state: MutableLiveData<SplashState> = MutableLiveData()
}