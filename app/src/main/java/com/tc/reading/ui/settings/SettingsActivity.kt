package com.tc.reading.ui.settings

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.tc.reading.App
import com.tc.reading.BaseActivity
import com.tc.reading.R
import com.tc.reading.util.ScreenUtil
import com.tc.reading.databinding.ActivitySettingsBinding

class SettingsActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenUtil.makeActivityFullScreen(this)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appContext = (application as App).getAppContext()
        binding.idServerAddress.setText(appContext.getAppSettings().getServerAddress())
        binding.idSettingsSave.setOnClickListener {
            appContext.getAppSettings().setServerAddress(binding.idServerAddress.text.toString())
        }

    }

}