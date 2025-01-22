package com.tc.reading

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
//import com.shuyu.gsyvideoplayer.player.PlayerFactory
//import com.shuyu.gsyvideoplayer.player.SystemPlayerManager
import com.tc.reading.databinding.ActivityMainBinding
import com.tc.reading.ui.TabsFragmentPagerAdapter
import com.tc.reading.ui.me.AboutMeActivity
import com.tc.reading.ui.settings.SettingsActivity
import com.tc.reading.util.ScreenUtil


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenUtil.makeActivityFullScreen(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = TabsFragmentPagerAdapter(supportFragmentManager)
        binding.topBar.setupWithViewPager(binding.viewPager)

        binding.idNavSelf.setOnClickListener {
            startActivity(Intent(this, AboutMeActivity::class.java))
        }

        binding.idNavSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}