package org.msarpong.openweather.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.snackbar.Snackbar
import org.msarpong.openweather.R
import org.msarpong.openweather.datamapping.WeatherResponse
import org.msarpong.openweather.ui.setting.SettingScreen
import org.msarpong.openweather.utils.*
import kotlin.math.roundToInt


class MainScreen : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var progressBar: ProgressBar

    private lateinit var menuButton: ImageButton

    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var baseLayout: CoordinatorLayout

    private lateinit var cityWeather: TextView
    private lateinit var dateWeather: TextView
    private lateinit var tempWeather: TextView
    private lateinit var descriptionWeather: TextView
    private lateinit var tempMinMaxWeather: TextView
    private lateinit var humidityWeather: TextView
    private lateinit var sunsetSunriseWeather: TextView
    private lateinit var windWeather: TextView
    private lateinit var iconWeather: ImageView
    private var hourSunsetSunrise: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        setupViews()
    }

    private fun setupViews() {
        progressBar = findViewById(R.id.progressBar)
        menuButton = findViewById(R.id.menu_btn)
        baseLayout = findViewById(R.id.base_layout)
        menuButton.setOnClickListener {
            val intent = Intent(this, SettingScreen::class.java)
            startActivity(intent)
        }
        swipeRefresh = findViewById(R.id.refresh_mainscreen)

        swipeRefresh.setOnRefreshListener(OnRefreshListener {
            viewModel.send(MainEvent.Load)
            Log.d("REFRESH", viewModel.send(MainEvent.Load).toString())
        })

        iconWeather = findViewById(R.id.weather_icon_imageview)

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
                    swipeRefresh.isRefreshing = false
                }
            }
        })

        viewModel.send(MainEvent.Load)
    }

    private fun showWeather(response: WeatherResponse) {

        Log.d("MainScreen", "hour: " + getHour())

        val isDay = comparisonHour()
        if (isDay) {
            Log.d("MainScreen", "TIME: DAY")
            sunsetSunriseWeather.text = convertTimestamp(response.sys.sunset.toLong())

            val icon = response.weather[0].id
            when (icon) {
                in 200..232 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm_day))
                in 300..321 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_drizzle))
                in 500..531 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_rain_day))
                in 600..622 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_snow_day)) //SNOW
                in 700..781 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_cloud)) //CLOUD/ATMO
                800 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_clear_day))//CLEAR
                in 801..804 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_cloudy_day)) //CLOUD
            }
        } else {
            Log.d("MainScreen", "TIME: NIGHT")
            sunsetSunriseWeather.text = convertTimestamp(response.sys.sunrise.toLong())

            val icon = response.weather[0].id
            when (icon) {
                in 200..232 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_thunderstorm_night))
                in 300..321 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_drizzle))
                in 500..531 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_rain_night))
                in 600..622 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_snow_night)) //SNOW
                in 700..781 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_cloud)) //CLOUD/ATMO
                800 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_clear_night))//CLEAR
                in 801..804 -> iconWeather.setImageDrawable(getDrawable(R.drawable.ic_cloudy_night)) //CLOUD
            }

        }




        cityWeather.text = response.name
        tempWeather.text = getString(R.string.tempWeather, response.main.temp.roundToInt(), UNIT_CELSIUS)
        dateWeather.text = capitalize(getDate(FULLDATETIME))
        descriptionWeather.text = response.weather[0].description.capitalize()
        tempMinMaxWeather.text = getString(
            R.string.tempMinMax,
            response.main.tempMin.roundToInt(),
            response.main.tempMax.roundToInt(),
            UNIT_CELSIUS
        )
        windWeather.text = getString(R.string.windWeather, response.wind.speed)
        humidityWeather.text = getString(R.string.humidityWeather, response.main.humidity)
    }


    private fun showError(error: Throwable) {
        Log.d("MainActivity", "showError")

        Snackbar.make(baseLayout, "error: $error", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry", View.OnClickListener {
                viewModel.send(MainEvent.Load)
            })
            .show()
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

}
