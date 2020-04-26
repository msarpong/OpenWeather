package org.msarpong.openweather.di

import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.msarpong.openweather.ui.main.MainViewModel
import org.msarpong.openweather.ui.setting.SettingViewModel

val androidComponents = module {
    single { androidContext().resources }
}

val viewModels = module {
    viewModel { MainViewModel(get()) }
    viewModel { SettingViewModel(get()) }

}
