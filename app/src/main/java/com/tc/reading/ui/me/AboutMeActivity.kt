package com.tc.reading.ui.me

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tc.reading.BaseActivity
import com.tc.reading.R
import com.tc.reading.databinding.ActivityAboutMeBinding
import com.tc.reading.util.ScreenUtil

class AboutMeActivity : BaseActivity() {

    private lateinit var binding: ActivityAboutMeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenUtil.makeActivityFullScreen(this)
        binding = ActivityAboutMeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}