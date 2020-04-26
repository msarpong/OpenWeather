package org.msarpong.openweather.ui.setting

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import org.msarpong.openweather.BuildConfig
import org.msarpong.openweather.R
import org.msarpong.openweather.ui.main.MainScreen
import org.msarpong.openweather.utils.*

class SettingScreen : AppCompatActivity() {

    private val viewModel: SettingViewModel by inject()
    private val prefs: KeyValueStorage by inject()


    private lateinit var backutton: ImageButton
    private lateinit var unitSwitch: Switch
    private lateinit var notificationSwitch: Switch
    private lateinit var uiSwitch: Switch

    private lateinit var versionText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_screen)
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

        unitSwitch.isChecked = prefs.getBoolean(UNIT_TEMP, CELSIUS)

        unitSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.send(SettingEvent.Unit(true))
            } else {
                viewModel.send(SettingEvent.Unit(false))
            }
        }

        notificationSwitch = findViewById(R.id.switchNotification)

        notificationSwitch.isChecked = prefs.getBoolean(NOTIFICATION, ON)

        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.send(SettingEvent.Notifications(true))
            } else {
                viewModel.send(SettingEvent.Notifications(false))
            }
        }

        uiSwitch = findViewById(R.id.switchUI)

        uiSwitch.isChecked = prefs.getBoolean(DARK_THEME, ON)

        uiSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.send(SettingEvent.DarkTheme(true))
            } else {
                viewModel.send(SettingEvent.DarkTheme(false))
            }
        }
        versionText = findViewById(R.id.version_textview)
        versionText.text =
            getString(R.string.version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }
}
