package org.msarpong.openweather.ui.setting

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import org.msarpong.openweather.R
import org.msarpong.openweather.ui.main.MainScreen
import org.msarpong.openweather.utils.SHARED_PREFS

class SettingScreen : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel

    private lateinit var backutton: ImageButton

    private lateinit var unitSwitch: Switch

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_screen)
        viewModel = ViewModelProviders.of(this)[SettingViewModel::class.java]

        sharedPrefs = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        setupViews()
    }

    private fun setupViews() {
        backutton = findViewById(R.id.close_btn)

        backutton.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()
        }

        unitSwitch = findViewById(R.id.switchUnit)

        unitSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.send(SettingEvent.Unit(true))
            } else {
                viewModel.send(SettingEvent.Unit(false))
            }
        }
    }
}
