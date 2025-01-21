package com.tc.reading

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
//import com.shuyu.gsyvideoplayer.player.PlayerFactory
//import com.shuyu.gsyvideoplayer.player.SystemPlayerManager
import com.tc.reading.databinding.ActivityMainBinding
import com.tc.reading.ui.TabsFragmentPagerAdapter
import com.tc.reading.util.ScreenUtil


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var appContext: AppContext;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenUtil.makeActivityFullScreen(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appContext = (application as App).getAppContext()
        binding.viewPager.adapter = TabsFragmentPagerAdapter(supportFragmentManager)
        binding.topBar.setupWithViewPager(binding.viewPager)
    }
}