package com.tc.reading

import android.app.Application
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.player.SystemPlayerManager
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager


class App : Application() {

    private lateinit var appContext: AppContext

    override fun onCreate() {
        super.onCreate()
        appContext = AppContext(applicationContext)

        //EXOPlayer内核，支持格式更多
        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)
        //PlayerFactory.setPlayManager(SystemPlayerManager::class.java)
        //PlayerFactory.setPlayManager(IjkPlayerManager::class.java)

    }

    fun getAppContext(): AppContext {
        return appContext;
    }

}