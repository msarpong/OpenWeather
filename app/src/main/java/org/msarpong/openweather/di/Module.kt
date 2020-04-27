package org.msarpong.openweather.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.msarpong.openweather.service.WeatherService
import org.msarpong.openweather.ui.main.MainViewModel
import org.msarpong.openweather.ui.setting.SettingViewModel
import org.msarpong.openweather.utils.KeyValueStorageFactory

val androidComponents = module {
    single { androidContext().resources }
    single { KeyValueStorageFactory.build(context = androidContext(), name = "openweather_prefs") }
}

val appComponents = module {
    single { WeatherService() }
}
val viewModels = module {
    viewModel { MainViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}
