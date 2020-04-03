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
import org.msarpong.openweather.utils.FULLDATETIME
import org.msarpong.openweather.utils.getDate
import org.threeten.bp.Instant
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
//        val icon = response.weather[0].icon.toInt()
//        when (icon) {
//            in 200..232 -> TODO() //THUNDERSTORM
//            in 300..321 -> TODO() //Drizzle
//            in 500..531 -> TODO() //RAIN
//            in 600..622 -> TODO() //SNOW
//            in 700..781 -> TODO() //CLOUD/ATMO
//            800 -> TODO() //CLEAR
//            in 801..804 -> TODO() //CLOUD
//        }

        val fromUnixTimestamp: Instant = Instant.ofEpochSecond(response.sys.sunrise.toLong())
        cityWeather.text = response.name
        tempWeather.text = response.main.temp.roundToInt().toString() + "°"
        dateWeather.text = capitalize(getDate(FULLDATETIME))
        descriptionWeather.text = response.weather[0].description.capitalize()
        tempMinMaxWeather.text = response.main.tempMin.roundToInt()
            .toString() + "° - " + response.main.tempMax.roundToInt().toString() + "°"
        windWeather.text = response.wind.speed.toString()
        humidityWeather.text = response.main.humidity.toString()
        sunsetSunriseWeather.text = fromUnixTimestamp.toString()
    }

    fun capitalize(str: String): String? {

        /* The first thing we do is remove whitespace from string */
        val c = str.replace("\\s+".toRegex(), " ")
        val s = c.trim { it <= ' ' }
        var l = ""
        var i = 0
        while (i < s.length) {
            if (i == 0) {                              /* Uppercase the first letter in strings */
                l += s.toUpperCase()[i]
                i++ /* To i = i + 1 because we don't need to add
                                                    value i = 0 into string l */
            }
            l += s[i]
            if (s[i]
                    .toInt() == 32
            ) {                   /* If we meet whitespace (32 in ASCII Code is whitespace) */
                l += s.toUpperCase()[i + 1] /* Uppercase the letter after whitespace */
                i++ /* Yo i = i + 1 because we don't need to add
                                                   value whitespace into string l */
            }
            i++
        }
        return l
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
