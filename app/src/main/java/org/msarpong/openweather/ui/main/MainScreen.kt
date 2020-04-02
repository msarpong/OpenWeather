package org.msarpong.openweather.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.msarpong.openweather.R
import org.msarpong.openweather.datamapping.WeatherResponse
import kotlin.math.roundToInt


class MainScreen : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var progressBar: ProgressBar

    private lateinit var cityWeather: TextView
    private lateinit var dateWeather: TextView
    private lateinit var tempWeather: TextView
    private lateinit var descriptionWeather: TextView
    private lateinit var tempMinMaxWeather: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        setupViews()
    }

    private fun setupViews() {
        progressBar = findViewById(R.id.progressBar)
        dateWeather = findViewById(R.id.date_textview)
        tempWeather = findViewById(R.id.temp_textview)
        descriptionWeather = findViewById(R.id.descrption_textview)
        tempMinMaxWeather = findViewById(R.id.temp_min_max_textview)
        cityWeather = findViewById(R.id.city_textview)
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
        Log.d("GiphyActivity", "showGifs: $response")
        cityWeather.text = response.name
        tempWeather.text = response.main.temp.roundToInt().toString()+"°"
        dateWeather.text = "Wednesday - 01 April"
        descriptionWeather.text = response.weather[0].description.capitalize()
        tempMinMaxWeather.text =response.main.tempMin.roundToInt().toString()+"° - "+response.main.tempMax.roundToInt().toString()+"°"
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
