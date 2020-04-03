package org.msarpong.openweather.utils

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class Application : Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        instance = this
    }
}