package org.msarpong.openweather.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.msarpong.openweather.R
import org.msarpong.openweather.datamapping.WeatherResponse
import org.msarpong.openweather.ui.setting.SettingScreen
import kotlin.math.roundToInt


class MainScreen : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var progressBar: ProgressBar

    private lateinit var menuButton: ImageButton

    private lateinit var cityWeather: TextView
    private lateinit var dateWeather: TextView
    private lateinit var tempWeather: TextView
    private lateinit var descriptionWeather: TextView
    private lateinit var tempMinMaxWeather: TextView
    private lateinit var humidityWeather: TextView
    private lateinit var sunsetSunriseWeather: TextView
    private lateinit var windWeather: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        setupViews()
    }

    private fun setupViews() {
        progressBar = findViewById(R.id.progressBar)

        menuButton = findViewById(R.id.menu_btn)
        menuButton.setOnClickListener {
            val intent = Intent(this, SettingScreen::class.java)
            startActivity(intent)
        }

        dateWeather = findViewById(R.id.date_textview)
        tempWeather = findViewById(R.id.temp_textview)
        descriptionWeather = findViewById(R.id.description_textview)
        tempMinMaxWeather = findViewById(R.id.temp_min_max_textview)
        cityWeather = findViewById(R.id.city_textview)
        humidityWeather = findViewById(R.id.humidity_textview)
        sunsetSunriseWeather = findViewById(R.id.sunset_sunrise_textview)
        windWeather = findViewById(R.id.wind_textview)
    }

    override fun onStart() {
        super.onStart()
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is MainState.InProgress -> showProgress()
                is MainState.Error -> {
                    hideProgress()
                    showError(state.error)
                }
                is MainState.Success -> {
                    hideProgress()
                    showWeather(state.weatherList)
                }
            }
        })

        viewModel.send(MainEvent.Load)
    }

    private fun showWeather(response: WeatherResponse) {
        Log.d("MainScreen", "showWeather: $response")
        cityWeather.text = response.name
        tempWeather.text = response.main.temp.roundToInt().toString() + "°"
        dateWeather.text = "Wednesday - 01 April"
        descriptionWeather.text = response.weather[0].description.capitalize()
        tempMinMaxWeather.text = response.main.tempMin.roundToInt()
            .toString() + "° - " + response.main.tempMax.roundToInt().toString() + "°"
        windWeather.text = response.wind.speed.toString()
        humidityWeather.text = response.main.humidity.toString()
        sunsetSunriseWeather.text = response.sys.sunrise.toString()
    }

    private fun showError(error: Throwable) {

    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

}
