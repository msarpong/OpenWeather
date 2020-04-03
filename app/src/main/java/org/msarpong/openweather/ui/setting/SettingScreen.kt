package org.msarpong.openweather.ui.setting

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import org.msarpong.openweather.R
import org.msarpong.openweather.ui.main.MainScreen

class SettingScreen : AppCompatActivity() {

    private lateinit var viewModel: SettingViewModel

    private lateinit var backutton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_screen)
        viewModel = ViewModelProviders.of(this)[SettingViewModel::class.java]
        setupViews()
    }

    private fun setupViews() {
        backutton = findViewById(R.id.close_btn)
        backutton.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}
