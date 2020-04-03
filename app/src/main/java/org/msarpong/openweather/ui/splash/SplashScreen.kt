package org.msarpong.openweather.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import org.msarpong.openweather.BuildConfig
import org.msarpong.openweather.R
import org.msarpong.openweather.ui.main.MainScreen
import org.msarpong.openweather.utils.MAIN_SCREEN_TIME

class SplashScreen : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    private lateinit var versionCode: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        viewModel = ViewModelProviders.of(this)[SplashViewModel::class.java]
        setupViews()
        goToMain()
    }

    private fun setupViews() {
        versionCode = findViewById(R.id.version_textview)
        versionCode.text = BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")"
    }

    private fun goToMain() {
        val runnableOnboarding = Runnable {
            val main = Intent(this, MainScreen::class.java)
            startActivity(main)
            finish()
        }
        Handler().postDelayed(runnableOnboarding, MAIN_SCREEN_TIME)
    }

}
